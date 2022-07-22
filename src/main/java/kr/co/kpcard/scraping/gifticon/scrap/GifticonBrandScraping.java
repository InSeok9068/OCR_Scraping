package kr.co.kpcard.scraping.gifticon.scrap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class GifticonBrandScraping {
    public static List<String> scraping(WebDriver driver) {
        List<WebElement> webElementList = driver.findElements(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[3]/div/div[3]/ul/li/a"));

        return webElementList
                .stream()
                .map(webElement -> webElement.getAttribute("href"))
                .collect(Collectors.toList());
    }
}
