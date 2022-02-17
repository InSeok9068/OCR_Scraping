package kr.co.kpcard.scraping.kakao;

import kr.co.kpcard.scraping.common.ScrapingUtil;
import kr.co.kpcard.scraping.kakao.domain.KakaoProductInfo;
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
public class KakaoProductInfoExtractScraping {
    public static KakaoProductInfo scraping(WebDriver driver) throws IOException {
        String title = StringUtils.EMPTY;
        String brand = StringUtils.EMPTY;
        String price = StringUtils.EMPTY;
        String content = StringUtils.EMPTY;
        String couponType = StringUtils.EMPTY;
        String imageSrc = StringUtils.EMPTY;
        String fileName = StringUtils.EMPTY;

        try {
            title = driver.findElement(By.className("tit_subject")).getText();
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }
        try {
            brand = driver.findElement(By.xpath("/html/body/app-root/app-view-wrapper/div/div/main/article/app-home/div/app-ct-main/div/div/div[2]/div/div[4]/gl-link")).getAttribute("data-tiara-copy");
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }
        try {
            price = driver.findElement(By.className("txt_total")).getText();
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }
        try {
            content = driver.findElement(By.className("desc_explain")).getText();
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }
        try {
            couponType = (title.contains("원권")) ? "금액권" : "교환권";
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }
        try {
            imageSrc = driver.findElement(By.xpath("/html/body/app-root/app-view-wrapper/div/div/main/article/app-home/div/app-ct-main/div/div/div[1]/div/cu-carousel/div/div/div/div/img")).getAttribute("src");
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        String outputFilePath = "C:\\excel\\image\\";

        try {
            if (!StringUtils.isEmpty(imageSrc)) {

                URL url = new URL(imageSrc);

                String ext = FilenameUtils.getExtension(imageSrc); // 이미지 확장자 추출

                if (StringUtils.isEmpty(ext)) {
                    ext = "jpg";
                }

                BufferedImage img = ImageIO.read(url);

                fileName = (ScrapingUtil.FILE_NAME_INDEX++) + "." + ext;

                String saveImagePath = outputFilePath + fileName;

                ImageIO.write(img, ext, new File(saveImagePath));
            }
        } catch (Exception exception) {
            log.error("이미지 쓰기 에러 : {}", ExceptionUtils.getStackTrace(exception));
        }

        return KakaoProductInfo.builder()
                .brand(brand)
                .title(title)
                .price(price)
                .content(content)
                .couponType(couponType)
                .imageFileName(fileName)
                .build();
    }
}
