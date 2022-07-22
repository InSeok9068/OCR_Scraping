package kr.co.kpcard.scraping.gifticon.scrap;

import kr.co.kpcard.scraping.common.domain.ScrapProductInfo;
import kr.co.kpcard.scraping.common.util.ScrapUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@Slf4j
public class GifticonProductInfoExtractScraping {
    public static ScrapProductInfo scraping(WebDriver driver) throws IOException {
        String title = StringUtils.EMPTY;
        String brand = StringUtils.EMPTY;
        String price = StringUtils.EMPTY;
        String imageSrc = StringUtils.EMPTY;
        String fileName = StringUtils.EMPTY;

        try {
            title = driver.findElement(By.className("pdt_name")).getText();
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        try {
            brand = driver.findElement(By.className("shopna")).getText();
            brand = brand.substring(0, brand.indexOf("매장교환") - 1);
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        try {
            price = driver.findElement(By.className("cost")).getText();
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        try {
            imageSrc = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[2]/div[1]/div/span/img")).getAttribute("src");
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        String outputFilePath = "C:\\excel\\image\\";

        try {
            if (!StringUtils.isEmpty(imageSrc)) {

                URL url = new URL(imageSrc);

                String ext = FilenameUtils.getExtension(imageSrc); // 이미지 확장자 추출

                if (StringUtils.isEmpty(ext) || ext.contains("net")) {
                    ext = "jpg";
                }

                BufferedImage img = ImageIO.read(url);

                fileName = "gifticon_" + ScrapUtil.getUniqueFileName() + "." + ext;

                String saveImagePath = outputFilePath + fileName;

                ImageIO.write(img, ext, new File(saveImagePath));
            }
        } catch (Exception exception) {
            log.error("이미지 쓰기 에러 : {}", ExceptionUtils.getStackTrace(exception));
        }

        return ScrapProductInfo.builder()
                .issuer("기프티콘")
                .title(title)
                .brand(brand)
                .subBrand(brand)
                .category(StringUtils.EMPTY)
                .couponType(StringUtils.EMPTY)
                .price(price)
                .content(StringUtils.EMPTY)
                .image(fileName)
                .build();
    }
}
