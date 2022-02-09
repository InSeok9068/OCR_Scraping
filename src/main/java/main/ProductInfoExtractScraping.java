package main;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class ProductInfoExtractScraping {
    public static Map<String, String> scraping(WebDriver driver) {
        String title = driver.findElement(By.className("tit_subject")).getText();
        String brand = driver.findElement(By.xpath("/html/body/app-root/app-view-wrapper/div/div/main/article/app-home/div/app-ct-main/div/div/div[2]/div/div[4]/gl-link")).getAttribute("data-tiara-copy");
        String price = driver.findElement(By.className("txt_total")).getText();
        String content = driver.findElement(By.className("desc_explain")).getText();

        return ImmutableMap.<String, String>builder()
                .put("brand", brand)
                .put("title", title)
                .put("price", price)
                .put("content", content)
                .build();
    }
}
