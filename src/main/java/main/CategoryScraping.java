package main;

import main.domain.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryScraping {
    private static final String CATEGORY_URL = "https://gift.kakao.com/brand/category/91/subcategory/";

    public static List<Category> scraping(WebDriver driver) {
        List<WebElement> webElementList = driver.findElements(By.className("link_midcate"));

        return webElementList
                .stream()
                .map(webElement ->
                        Category.builder()
                                .name(webElement.getAttribute("data-tiara-copy"))
                                .url(CATEGORY_URL + webElement.getAttribute("data-tiara-id"))
                                .build())
                .collect(Collectors.toList());
    }
}
