package kr.co.kpcard.scraping.gifticon.scrap;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GifticonProductScraping {
    public static List<String> scraping(WebDriver driver) throws InterruptedException {
        List<String> productList = new ArrayList<>();

        List<WebElement> webElementList = driver.findElements(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[7]/div[2]/a"));

        webElementList = webElementList.stream().filter(webElement -> (StringUtils.isEmpty(webElement.getAttribute("class")) || webElement.getAttribute("class").equals("red"))).collect(Collectors.toList());

        for (int i = 1; i <= webElementList.size(); i++) {
            WebElement webElement = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[7]/div[2]/a[" + (i + 2) + "]"));
            webElement.click();
            Thread.sleep(1000);

            List<WebElement> webElementProductList = driver.findElements(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[7]/div[1]/ul/li/a"));
            webElementProductList.forEach(webElementProduct -> productList.add(webElementProduct.getAttribute("href")));
        }

        return productList;
    }
}
