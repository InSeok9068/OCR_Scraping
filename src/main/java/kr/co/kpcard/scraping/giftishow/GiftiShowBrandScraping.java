package kr.co.kpcard.scraping.giftishow;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class GiftiShowBrandScraping {
    private static final String BRAND_URL = "https://www.giftishow.com/brand/brandGoodsList.mhows?brand_no=%s";

    public static List<String> scraping(WebDriver driver) {
        List<WebElement> webElementList = driver.findElements(By.xpath("/html/body/div[1]/div[2]/div[2]/div[2]/div/ul/li/a"));

        return webElementList
                .stream()
                .map(webElement -> {
                    String href = webElement.getAttribute("href");
                    return String.format(BRAND_URL, href.substring((href.lastIndexOf("=") + 1), href.lastIndexOf('%')));
                })
                .collect(Collectors.toList());
    }
}