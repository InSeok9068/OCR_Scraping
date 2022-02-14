package kr.co.kpcard.scraping.kakao;

import kr.co.kpcard.scraping.common.ChromDriverStart;
import kr.co.kpcard.scraping.common.ScrapingUtil;
import kr.co.kpcard.scraping.kakao.domain.KakaoBrand;
import kr.co.kpcard.scraping.kakao.domain.KakaoCategory;
import kr.co.kpcard.scraping.kakao.domain.KakaoProduct;
import kr.co.kpcard.scraping.kakao.domain.KakaoProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class KakaoScrapingMain {
    public static void main(String[] args) {

        final WebDriver driver = ChromDriverStart.createWebDriver(false);

        // 크롤링 시작
        try {
            // 초기 페이지 로딩
            driver.get("https://gift.kakao.com/brand/category/91/subcategory/146");

            // 초기 페이지 로딩 시간 2초 정도 딜레이
            Thread.sleep(2000);

            // 카테고리 URL 스크래핑 진행
            List<KakaoCategory> kakaoCategoryList = KakaoCategoryScraping.scraping(driver);

            List<KakaoBrand> kakaoBrandList = new ArrayList<>();

//            for (Category category : categoryList) {
            for (KakaoCategory kakaoCategory : kakaoCategoryList) {
                ScrapingUtil.openNewTab(driver, kakaoCategory.getUrl());
                String tab1 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ZERO);
                String tab2 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ONE);
                driver.switchTo().window(tab2).navigate();

                List<KakaoBrand> kakaoBrands = KakaoBrandScraping.scraping(driver);
                kakaoBrands.forEach(kakaoBrand -> kakaoBrand.setCategoryName(kakaoCategory.getName()));

                kakaoBrandList.addAll(kakaoBrands);
                driver.close();
                driver.switchTo().window(tab1).navigate();
            }

            kakaoBrandList = kakaoBrandList.stream().distinct().collect(Collectors.toList());

            List<KakaoProduct> kakaoProductList = new ArrayList<>();

//            for (Brand brand : brandList) {
            for (KakaoBrand kakaoBrand : kakaoBrandList) {
                ScrapingUtil.openNewTab(driver, kakaoBrand.getUrl());
                String tab1 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ZERO);
                String tab2 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ONE);
                driver.switchTo().window(tab2).navigate();

                List<KakaoProduct> kakaoProducts = KakaoProductScraping.scraping(driver);
                kakaoProducts.forEach(kakaoProduct -> kakaoProduct.setCategoryName(kakaoBrand.getCategoryName()));

                kakaoProductList.addAll(kakaoProducts);
                driver.close();
                driver.switchTo().window(tab1).navigate();
            }

            List<KakaoProductInfo> kakaoProductInfoList = new ArrayList<>();

            for (KakaoProduct kakaoProduct : kakaoProductList) {
                ScrapingUtil.openNewTab(driver, kakaoProduct.getUrl());
                String tab1 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ZERO);
                String tab2 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ONE);
                driver.switchTo().window(tab2).navigate();

                KakaoProductInfo kakaoProductInfo = KakaoProductInfoExtractScraping.scraping(driver);
                kakaoProductInfo.setCategoryName(kakaoProduct.getCategoryName());

                kakaoProductInfoList.add(kakaoProductInfo);
                driver.close();
                driver.switchTo().window(tab1).navigate();
            }

            for (KakaoCategory kakaoCategory : kakaoCategoryList) {
                KakaoExcel.create(kakaoProductInfoList.stream().filter(kakaoProductInfo -> kakaoProductInfo.getCategoryName().equals(kakaoCategory.getName())).collect(Collectors.toList()), kakaoCategory.getName().replaceAll("/", "_"));
            }
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        } finally {
            driver.quit();
        }
    }
}
