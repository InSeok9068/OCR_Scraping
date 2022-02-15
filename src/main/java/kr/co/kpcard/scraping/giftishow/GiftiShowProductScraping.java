package kr.co.kpcard.scraping.giftishow;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class GiftiShowProductScraping {
    private static final String PRODUCT_URL = "https://www.giftishow.com/brand/brandGoodsDetail.mhows?goods_seq=%s&brand_no=%s";

    public static List<String> scraping(WebDriver driver) throws InterruptedException {
        List<String> productList = new ArrayList<>();

        List<WebElement> webElementList = driver.findElements(By.xpath("/html/body/div[1]/form[2]/div/div[3]/span/a"));

        for (int i = 1; i <= webElementList.size(); i++) {
            ((JavascriptExecutor) driver).executeScript("goPageNo('no')".replace("no", String.valueOf(i)));
            Thread.sleep(1000);

            List<WebElement> webElementProductList = driver.findElements(By.xpath("/html/body/div[1]/form[2]/div/div[2]/div[2]/ul/li/a"));
            webElementProductList.forEach(webElementProduct -> {
                String href = webElementProduct.getAttribute("href");
                String brand_no = href.substring(href.indexOf('\'') + 1, href.indexOf(',') - 1);
                String goods_seq = href.substring(href.indexOf(',') + 3, href.lastIndexOf('\''));
                productList.add(String.format(PRODUCT_URL, goods_seq, brand_no));
            });
        }

        return productList;
    }
}
