package main;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class Main {
    public static void main(String[] args) {

        final WebDriver driver = ChromDriverStart.createWebDriver(false);

        // 크롤링 시작
        try {
            // 초기 페이지 로딩
            driver.get("https://gift.kakao.com/brand/category/91/subcategory/146");

            // 초기 페이지 로딩 시간 2초 정도 딜레이
            Thread.sleep(2000);

            // 카테고리 URL 스크래핑 진행
            List<String> categoryUrlList = CategoryScraping.scraping(driver);

            List<String> brandUrlList = new ArrayList<>();

            for (String categoryUrl : categoryUrlList) {
//            for (String categoryUrl : categoryUrlList.subList(0, 1)) {
                ScrapingUtil.openNewTab(driver, categoryUrl);
                String tab1 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ZERO);
                String tab2 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ONE);
                driver.switchTo().window(tab2).navigate();
                brandUrlList.addAll(BrandScraping.scraping(driver));
                driver.close();
                driver.switchTo().window(tab1).navigate();
            }

            brandUrlList = brandUrlList.stream().distinct().collect(Collectors.toList());

            List<String> productUrlList = new ArrayList<>();

            for (String brandUrl : brandUrlList) {
//            for (String brandUrl : brandUrlList.subList(13, 14)) {
                ScrapingUtil.openNewTab(driver, brandUrl);
                String tab1 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ZERO);
                String tab2 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ONE);
                driver.switchTo().window(tab2).navigate();
                productUrlList.addAll(ProductScraping.scraping(driver));
                driver.close();
                driver.switchTo().window(tab1).navigate();
            }

            List<Map<String, String>> productInfoList = new ArrayList<>();

            for (String productUrl : productUrlList) {
                ScrapingUtil.openNewTab(driver, productUrl);
                String tab1 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ZERO);
                String tab2 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ONE);
                driver.switchTo().window(tab2).navigate();
                productInfoList.add(ProductInfoExtractScraping.scraping(driver));
                driver.close();
                driver.switchTo().window(tab1).navigate();
            }

            Excel.create(productInfoList);
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        } finally {
            driver.quit();
        }
    }
}
