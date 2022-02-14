package kr.co.kpcard.scraping.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;

@Slf4j
public class ChromDriverStart {

    public static WebDriver createWebDriver(boolean isHide) {
        final File driverFile = new File("C:\\Users\\kpcard\\Desktop\\크롤링\\chromedriver.exe");

        final ChromeDriverService chromeDriverService = new ChromeDriverService.Builder()
                .usingDriverExecutable(driverFile)
                .usingAnyFreePort()
                .build();

        ChromeOptions chromeOptions = new ChromeOptions();
        if (isHide) {
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--no-sandbox");
        }

        try {
            chromeDriverService.start();
        } catch (
                IOException ioException) {
            log.error(ExceptionUtils.getStackTrace(ioException));
        }

        return new ChromeDriver(chromeDriverService, chromeOptions);
    }
}
