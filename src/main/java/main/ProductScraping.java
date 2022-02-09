package main;

import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public class ProductScraping {
    private static final String PRODUCT_URL = "https://gift.kakao.com/product/";

    public static List<String> scraping(WebDriver driver) throws InterruptedException {
        boolean isEvent = Boolean.TRUE;

        String eventXPath = "/html/body/app-root/app-view-wrapper/div/div/main/article/app-pw-detail/gl-promotion/div/gl-product-group[2]/div/div/div/ul/li";
        String xPath = "/html/body/app-root/app-view-wrapper/div/div/main/article/app-pw-detail/gl-promotion/div/gl-product-group/div/div/div/ul/li";

        int eventMinLiSize = driver.findElements(By.xpath(eventXPath)).size();
        int minLiSize = driver.findElements(By.xpath(xPath)).size();
        int maxLiSize = NumberUtils.INTEGER_ZERO;
        int preLiSize;

        do {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(500);
            preLiSize = maxLiSize;

            if (isEvent) {
                maxLiSize = driver.findElements(By.xpath(eventXPath)).size();
            } else {
                maxLiSize = driver.findElements(By.xpath(xPath)).size();
            }

            if (maxLiSize == 0) {
                maxLiSize = driver.findElements(By.xpath(xPath)).size();
                isEvent = Boolean.FALSE;
            }

            if (isEvent) {
                minLiSize = eventMinLiSize;
            }
        } while (minLiSize != maxLiSize && preLiSize != maxLiSize);

        // li 리스트 추출 (상품 리스트)
        List<WebElement> webElementList;
        if (isEvent) {
            webElementList = driver.findElements(By.xpath(eventXPath));
        } else {
            webElementList = driver.findElements(By.xpath(xPath));
        }

        return webElementList.stream().map(webElement -> PRODUCT_URL + webElement.getAttribute("data-tiara-id")).collect(Collectors.toList());
    }
}
