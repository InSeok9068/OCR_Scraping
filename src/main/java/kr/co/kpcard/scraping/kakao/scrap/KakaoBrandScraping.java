package kr.co.kpcard.scraping.kakao.scrap;

import kr.co.kpcard.scraping.kakao.domain.KakaoBrand;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class KakaoBrandScraping {
    public List<KakaoBrand> scraping(WebDriver driver) {
        List<WebElement> webElementList = driver.findElements(By.xpath("/html/body/app-root/app-view-wrapper/div/div/main/article/app-pw-home/article/div/div/app-brands/div/div/div[2]/div[1]/ngx-flicking/div/div[1]/div/div/gl-link/a"));

        return webElementList
                .stream()
                .filter(webElement -> !StringUtils.isEmpty(webElement.getAttribute("href")))
                .map(webElement ->
                        KakaoBrand.builder()
                                .url(webElement.getAttribute("href"))
                                .build())
                .collect(Collectors.toList());
    }
}
