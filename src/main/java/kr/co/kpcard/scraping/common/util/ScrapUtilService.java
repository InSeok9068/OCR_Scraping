package kr.co.kpcard.scraping.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Date;

@Slf4j
@Component
public class ScrapUtilService {

    @Value("${savePath.image}")
    private String imageSavePath;

    public static void openNewTab(WebDriver driver, String url) throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("window.open('url')".replace("url", url));
        Thread.sleep(1000);
    }

    public String saveImage(String imageSrc, String prefixFileName) {
        String fileName = StringUtils.EMPTY;

        try {
            if (!StringUtils.isEmpty(imageSrc)) {

                URL url = new URL(imageSrc);

                String ext = FilenameUtils.getExtension(imageSrc); // 이미지 확장자 추출

                if (StringUtils.isEmpty(ext) || ext.contains("net")) {
                    ext = "jpg";
                }

                BufferedImage img = ImageIO.read(url);

                fileName = prefixFileName + getUniqueFileName() + "." + ext;

//                String saveImagePath = imageSavePath + DateFormatUtils.format(new Date(), "yyyyMMdd") + '\\' + fileName;
                String saveImagePath = imageSavePath + fileName;

                ImageIO.write(img, ext, new File(saveImagePath));
            }
        } catch (Exception exception) {
            log.error("이미지 쓰기 에러 : {}", ExceptionUtils.getStackTrace(exception));
        }

        return fileName;
    }

    private static String getUniqueFileName() {
        return DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + RandomStringUtils.randomNumeric(5);
    }

}
