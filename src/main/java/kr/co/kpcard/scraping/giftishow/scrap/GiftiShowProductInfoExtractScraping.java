package kr.co.kpcard.scraping.giftishow.scrap;

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
public class GiftiShowProductInfoExtractScraping {

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
            title = driver.findElement(By.className("itemNm")).getText();
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        try {
            int pTagSize = driver.findElements(By.xpath("/html/body/div[3]/div/div[1]/div[1]/div/div[2]/div[1]/p")).size();

            if (pTagSize == 4) {
                brand = driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div[1]/div/div[2]/div[1]/p[3]")).getText();
            } else {
                brand = driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div[1]/div/div[2]/div[1]/p[2]")).getText();
            }
            brand = brand.substring(brand.indexOf(":") + 2);
        } catch (Exception exception) {
            try {
                int pTagSize = driver.findElements(By.xpath("/html/body/div[4]/div/div[1]/div[1]/div/div[2]/div[1]/p")).size();

                if (pTagSize == 4) {
                    brand = driver.findElement(By.xpath("/html/body/div[4]/div/div[1]/div[1]/div/div[2]/div[1]/p[3]")).getText();
                } else {
                    brand = driver.findElement(By.xpath("/html/body/div[4]/div/div[1]/div[1]/div/div[2]/div[1]/p[2]")).getText();
                }
                brand = brand.substring(brand.indexOf(":") + 2);
            } catch (Exception exception1) {
                log.error(ExceptionUtils.getStackTrace(exception));
            }
        }

        try {
            price = driver.findElement(By.className("price")).getText();
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        try {
            couponType = (title.contains("원권")) ? "금액권" : "교환권";
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        try {
            try {
                StringBuilder sb = new StringBuilder();
                content = sb.append(driver.findElement(By.className("txtCon")).getText())
                        .append(driver.findElement(By.className("pnotandum")).getText())
                        .toString();
            } catch (Exception exception) {
                log.error(ExceptionUtils.getStackTrace(exception));
            }
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        try {
            imageSrc = driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div[1]/div/div[1]/img")).getAttribute("src");
        } catch (Exception exception) {
            try {
                imageSrc = driver.findElement(By.xpath("/html/body/div[4]/div/div[1]/div[1]/div/div[1]/img")).getAttribute("src");
            } catch (Exception exception1) {
                log.error(ExceptionUtils.getStackTrace(exception));
            }
        }

        fileName = scrapUtilService.saveImage(imageSrc, "giftiShow_");

        return ScrapProductInfo.builder()
                .issuer("기프티쇼")
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
