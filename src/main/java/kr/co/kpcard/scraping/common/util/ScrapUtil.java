package kr.co.kpcard.scraping.common.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Date;

public class ScrapUtil {
    public static void openNewTab(WebDriver driver, String url) throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("window.open('url')".replace("url", url));
        Thread.sleep(1000);
    }

    public static String getUniqueFileName() {
        return DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + RandomStringUtils.randomNumeric(5);
    }
}
