package kr.co.kpcard.scraping.kakao.scrap;

import kr.co.kpcard.scraping.common.domain.ScrapProductInfo;
import kr.co.kpcard.scraping.common.util.ScrapUtilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoProductInfoExtractScraping {

    private final ScrapUtilService scrapUtilService;

    public ScrapProductInfo scraping(WebDriver driver) throws IOException {
        String title = StringUtils.EMPTY;
        String brand = StringUtils.EMPTY;
        String price = StringUtils.EMPTY;
        String content = StringUtils.EMPTY;
        String couponType = StringUtils.EMPTY;
        String imageSrc = StringUtils.EMPTY;
        String fileName;

        try {
            title = driver.findElement(By.className("tit_subject")).getText();
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }
        try {
            brand = driver.findElement(By.xpath("/html/body/app-root/app-view-wrapper/div/div/main/article/app-home/div/app-main/div/div/div[2]/div/div[4]/gl-link")).getAttribute("data-tiara-copy");
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }
        try {
            price = driver.findElement(By.className("txt_total")).getText();
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }
        try {
            couponType = (title.contains("원권")) ? "금액권" : "교환권";
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }
        try {
            content = driver.findElement(By.className("desc_explain")).getText();
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }
        try {
            imageSrc = driver.findElement(By.xpath("/html/body/app-root/app-view-wrapper/div/div/main/article/app-home/div/app-main/div/div/div[1]/div/ngx-flicking/div/div/img")).getAttribute("src");
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        fileName = scrapUtilService.saveImage(imageSrc, "kakao_");

        return ScrapProductInfo.builder()
                .issuer("카카오")
                .title(title)
                .brand(brand)
                .subBrand(brand)
                .category(StringUtils.EMPTY)
                .couponType(couponType)
                .price(price)
                .content(content)
                .image(fileName)
                .build();
    }
}
