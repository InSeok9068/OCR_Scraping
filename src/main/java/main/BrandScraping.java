package main;

import main.domain.Brand;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class BrandScraping {
    public static List<Brand> scraping(WebDriver driver) {
        List<WebElement> webElementList = driver.findElements(By.className("ng-star-inserted"));

        return webElementList
                .stream()
                .filter(webElement -> !StringUtils.isEmpty(webElement.getAttribute("href")))
                .map(webElement ->
                        Brand.builder()
                                .url(webElement.getAttribute("href"))
                                .build())
                .collect(Collectors.toList());
    }
}
