package kr.co.kpcard.scraping.giftishow;

import kr.co.kpcard.scraping.common.ChromDriverStart;
import kr.co.kpcard.scraping.common.ScrapingUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class GiftiShowScrapingMain {
    public static void main(String[] args) {
        final WebDriver driver = ChromDriverStart.createWebDriver(false);

        // 크롤링 시작
        try {
            // 초기 페이지 로딩
            driver.get("https://www.giftishow.com/brand/brandList.mhows");

            // 초기 페이지 로딩 시간 2초 정도 딜레이
            Thread.sleep(2000);

            List<String> brandUrlList = GiftiShowBrandScraping.scraping(driver);

            List<String> productUrlList = new ArrayList<>();

            for (String brandUrl : brandUrlList) {
                ScrapingUtil.openNewTab(driver, brandUrl);
                String tab1 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ZERO);
                String tab2 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ONE);
                driver.switchTo().window(tab2).navigate();

                productUrlList.addAll(GiftiShowProductScraping.scraping(driver));

                driver.close();
                driver.switchTo().window(tab1).navigate();
            }

            List<Map<String, String>> productInfoList = new ArrayList<>();

            for (String productUrl : productUrlList) {
                ScrapingUtil.openNewTab(driver, productUrl);
                String tab1 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ZERO);
                String tab2 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ONE);
                driver.switchTo().window(tab2).navigate();

                productInfoList.add(GiftiShowProductInfoExtractScraping.scraping(driver));

                driver.close();
                driver.switchTo().window(tab1).navigate();
            }

            GiftiShowExcel.create(productInfoList, "GiftiShow 상품정보");
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        } finally {
            driver.quit();
        }
    }
}
