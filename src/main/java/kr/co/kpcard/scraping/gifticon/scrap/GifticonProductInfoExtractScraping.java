package kr.co.kpcard.scraping.gifticon.scrap;

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
public class GifticonProductInfoExtractScraping {

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
            title = driver.findElement(By.className("pdt_name")).getText();
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        try {
            brand = driver.findElement(By.className("shopna")).getText();
            brand = brand.substring(0, brand.indexOf("매장교환") - 1);
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        try {
            price = driver.findElement(By.className("cost")).getText();
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        try {
            couponType = (title.contains("원권")) ? "금액권" : "교환권";
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        try {
            StringBuilder sb = new StringBuilder();
            content = sb.append(driver.findElement(By.id("con1")).getText())
                    .append(driver.findElement(By.id("con2")).getText())
                    .toString();
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        try {
            imageSrc = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[2]/div[1]/div/span/img")).getAttribute("src");
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        fileName = scrapUtilService.saveImage(imageSrc, "gifticon_");

        return ScrapProductInfo.builder()
                .issuer("기프티콘")
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
