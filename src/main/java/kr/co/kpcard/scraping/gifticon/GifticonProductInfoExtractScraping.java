package kr.co.kpcard.scraping.gifticon;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class GifticonProductInfoExtractScraping {
    public static Map<String, String> scraping(WebDriver driver) throws IOException {
        String title = StringUtils.EMPTY;
        String brand = StringUtils.EMPTY;
        String price = StringUtils.EMPTY;

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

        return ImmutableMap.<String, String>builder()
                .put("title", title)
                .put("brand", brand)
                .put("price", price)
                .build();
    }
}
