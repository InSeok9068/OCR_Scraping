package kr.co.kpcard.scraping.giftishow;

import com.google.common.collect.ImmutableMap;
import kr.co.kpcard.scraping.common.ScrapingUtil;
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
import java.util.Map;

@Slf4j
public class GiftiShowProductInfoExtractScraping {
    public static Map<String, String> scraping(WebDriver driver) throws IOException {
        String title = StringUtils.EMPTY;
        String brand = StringUtils.EMPTY;
        String price = StringUtils.EMPTY;
        String imageSrc = StringUtils.EMPTY;
        String fileName = StringUtils.EMPTY;

        try {
            title = driver.findElement(By.className("itemNm")).getText();
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        try {
            int pTagSize = driver.findElements(By.xpath("/html/body/div[3]/div/div[1]/div[1]/div/div[2]/div[1]/p")).size();

            if (pTagSize == 4) {
                brand = driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div[1]/div/div[2]/div[1]/p[3]")).getText();
            } else {
                brand = driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div[1]/div/div[2]/div[1]/p[2]")).getText();
            }
            brand = brand.substring(brand.indexOf(":") + 2);
        } catch (Exception exception) {
            try {
                int pTagSize = driver.findElements(By.xpath("/html/body/div[4]/div/div[1]/div[1]/div/div[2]/div[1]/p")).size();

                if (pTagSize == 4) {
                    brand = driver.findElement(By.xpath("/html/body/div[4]/div/div[1]/div[1]/div/div[2]/div[1]/p[3]")).getText();
                } else {
                    brand = driver.findElement(By.xpath("/html/body/div[4]/div/div[1]/div[1]/div/div[2]/div[1]/p[2]")).getText();
                }
                brand = brand.substring(brand.indexOf(":") + 2);
            } catch (Exception exception1) {
                log.error(ExceptionUtils.getStackTrace(exception));
            }
        }

        try {
            price = driver.findElement(By.className("price")).getText();
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        try {
            imageSrc = driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div[1]/div/div[1]/img")).getAttribute("src");
        } catch (Exception exception) {
            try {
                imageSrc = driver.findElement(By.xpath("/html/body/div[4]/div/div[1]/div[1]/div/div[1]/img")).getAttribute("src");
            } catch (Exception exception1) {
                log.error(ExceptionUtils.getStackTrace(exception));
            }
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

                fileName = "giftiShow_" + (ScrapingUtil.FILE_NAME_INDEX++) + "." + ext;

                String saveImagePath = outputFilePath + fileName;

                ImageIO.write(img, ext, new File(saveImagePath));
            }
        } catch (Exception exception) {
            log.error("이미지 쓰기 에러 : {}", ExceptionUtils.getStackTrace(exception));
        }

        return ImmutableMap.<String, String>builder()
                .put("title", title)
                .put("brand", brand)
                .put("price", price)
                .put("imageFileName", fileName)
                .build();
    }
}
