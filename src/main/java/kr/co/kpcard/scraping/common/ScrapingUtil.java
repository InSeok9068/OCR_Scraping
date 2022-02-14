package kr.co.kpcard.scraping.common;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class ScrapingUtil {
    public static int FILE_NAME_INDEX = 1;

    public static void openNewTab(WebDriver driver, String url) throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("window.open('url')".replace("url", url));
        Thread.sleep(1000);
    }
}
