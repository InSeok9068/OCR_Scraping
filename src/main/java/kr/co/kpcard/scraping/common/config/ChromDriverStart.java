package kr.co.kpcard.scraping.common.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Slf4j
@Component
public class ChromDriverStart {

    @Value("${chrome.hide}")
    private boolean isHide;

    @Bean
    public WebDriver createWebDriver() {
        final File driverFile = new File("C:\\chromedriver\\chromedriver.exe");

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
        } catch (IOException ioException) {
            log.error(ExceptionUtils.getStackTrace(ioException));
        }

        return new ChromeDriver(chromeDriverService, chromeOptions);
    }
}
