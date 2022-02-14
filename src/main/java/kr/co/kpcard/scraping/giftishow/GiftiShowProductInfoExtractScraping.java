package kr.co.kpcard.scraping.giftishow;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class GiftiShowProductInfoExtractScraping {
    public static Map<String, String> scraping(WebDriver driver) throws IOException {
        String title = StringUtils.EMPTY;
        String brand = StringUtils.EMPTY;

        try {
            title = driver.findElement(By.className("itemNm")).getText();
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        try {
            brand = driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div[1]/div/div[2]/div[1]/p[2]")).getText();
            brand = brand.substring(brand.indexOf(":") + 2);
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        return ImmutableMap.<String, String>builder()
                .put("title", title)
                .put("brand", brand)
                .build();
    }
}
