package kr.co.kpcard.scraping.gifticon.service;

import kr.co.kpcard.scraping.common.domain.ScrapProductInfo;
import kr.co.kpcard.scraping.common.excel.ExcelService;
import kr.co.kpcard.scraping.common.util.ScrapUtilService;
import kr.co.kpcard.scraping.gifticon.scrap.GifticonBrandScraping;
import kr.co.kpcard.scraping.gifticon.scrap.GifticonProductInfoExtractScraping;
import kr.co.kpcard.scraping.gifticon.scrap.GifticonProductScraping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GifticonService {

    private final WebDriver driver;
    private final ExcelService excelService;
    private final GifticonBrandScraping gifticonBrandScraping;
    private final GifticonProductScraping gifticonProductScraping;
    private final GifticonProductInfoExtractScraping gifticonProductInfoExtractScraping;

    public void scrap() {
        // 크롤링 시작
        try {
            // 초기 페이지 로딩
            driver.get("https://www.gifticon.com/shopping/shopping_brandshop.do");

            // 초기 페이지 로딩 시간 2초 정도 딜레이
            Thread.sleep(2000);

            List<String> brandUrlList = gifticonBrandScraping.scraping(driver);

            List<String> productUrlList = new ArrayList<>();

            for (String brandUrl : brandUrlList) {
                ScrapUtilService.openNewTab(driver, brandUrl);
                String tab1 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ZERO);
                String tab2 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ONE);
                driver.switchTo().window(tab2).navigate();

                productUrlList.addAll(gifticonProductScraping.scraping(driver));

                driver.close();
                driver.switchTo().window(tab1).navigate();
            }

            List<ScrapProductInfo> scrapProductInfoList = new ArrayList<>();

            for (String productUrl : productUrlList) {
                ScrapUtilService.openNewTab(driver, productUrl);
                String tab1 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ZERO);
                String tab2 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ONE);
                driver.switchTo().window(tab2).navigate();

                scrapProductInfoList.add(gifticonProductInfoExtractScraping.scraping(driver));

                driver.close();
                driver.switchTo().window(tab1).navigate();
            }

            excelService.create(scrapProductInfoList, "Gifticon 상품정보");
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        } finally {
            driver.quit();
        }
    }
}
