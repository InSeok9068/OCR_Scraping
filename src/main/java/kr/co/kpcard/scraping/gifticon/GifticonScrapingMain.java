package kr.co.kpcard.scraping.gifticon;

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
public class GifticonScrapingMain {
    public static void main(String[] args) {
        final WebDriver driver = ChromDriverStart.createWebDriver(false);

        // 크롤링 시작
        try {
            // 초기 페이지 로딩
            driver.get("https://www.gifticon.com/shopping/shopping_brandshop.do");

            // 초기 페이지 로딩 시간 2초 정도 딜레이
            Thread.sleep(2000);

            List<String> brandUrlList = GifticonBrandScraping.scraping(driver);

            List<String> productUrlList = new ArrayList<>();

            for (String brandUrl : brandUrlList) {
                ScrapingUtil.openNewTab(driver, brandUrl);
                String tab1 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ZERO);
                String tab2 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ONE);
                driver.switchTo().window(tab2).navigate();

                productUrlList.addAll(GifticonProductScraping.scraping(driver));

                driver.close();
                driver.switchTo().window(tab1).navigate();
            }

            List<Map<String, String>> productInfoList = new ArrayList<>();

            for (String productUrl : productUrlList) {
                ScrapingUtil.openNewTab(driver, productUrl);
                String tab1 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ZERO);
                String tab2 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ONE);
                driver.switchTo().window(tab2).navigate();

                productInfoList.add(GifticonProductInfoExtractScraping.scraping(driver));

                driver.close();
                driver.switchTo().window(tab1).navigate();
            }

            GifticonExcel.create(productInfoList, "Gifticon 상품정보");

        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        } finally {
            driver.quit();
        }
    }
}
