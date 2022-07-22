package kr.co.kpcard.scraping.kakao.service;

import kr.co.kpcard.scraping.common.domain.ScrapProductInfo;
import kr.co.kpcard.scraping.common.util.ExcelUtil;
import kr.co.kpcard.scraping.common.util.ScrapUtil;
import kr.co.kpcard.scraping.kakao.domain.KakaoBrand;
import kr.co.kpcard.scraping.kakao.domain.KakaoCategory;
import kr.co.kpcard.scraping.kakao.domain.KakaoProduct;
import kr.co.kpcard.scraping.kakao.scrap.KakaoBrandScraping;
import kr.co.kpcard.scraping.kakao.scrap.KakaoCategoryScraping;
import kr.co.kpcard.scraping.kakao.scrap.KakaoProductInfoExtractScraping;
import kr.co.kpcard.scraping.kakao.scrap.KakaoProductScraping;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {

    private final WebDriver driver;

    public void scrap() {
        // 크롤링 시작
        try {
            // 초기 페이지 로딩
            driver.get("https://gift.kakao.com/brand/category/91/subcategory/146");

            // 초기 페이지 로딩 시간 2초 정도 딜레이
            Thread.sleep(2000);

            // 카테고리 URL 스크래핑 진행
            List<KakaoCategory> kakaoCategoryList = KakaoCategoryScraping.scraping(driver);

            List<KakaoBrand> kakaoBrandList = new ArrayList<>();

            for (KakaoCategory kakaoCategory : kakaoCategoryList) {
                ScrapUtil.openNewTab(driver, kakaoCategory.getUrl());
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

            for (KakaoBrand kakaoBrand : kakaoBrandList) {
                ScrapUtil.openNewTab(driver, kakaoBrand.getUrl());
                String tab1 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ZERO);
                String tab2 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ONE);
                driver.switchTo().window(tab2).navigate();

                List<KakaoProduct> kakaoProducts = KakaoProductScraping.scraping(driver);
                kakaoProducts.forEach(kakaoProduct -> kakaoProduct.setCategoryName(kakaoBrand.getCategoryName()));

                kakaoProductList.addAll(kakaoProducts);
                driver.close();
                driver.switchTo().window(tab1).navigate();
            }

            List<ScrapProductInfo> scrapProductInfoList = new ArrayList<>();

            for (KakaoProduct kakaoProduct : kakaoProductList) {
                ScrapUtil.openNewTab(driver, kakaoProduct.getUrl());
                String tab1 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ZERO);
                String tab2 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ONE);
                driver.switchTo().window(tab2).navigate();

                ScrapProductInfo scrapProductInfo = KakaoProductInfoExtractScraping.scraping(driver);
                scrapProductInfo.setCategory(kakaoProduct.getCategoryName());

                scrapProductInfoList.add(scrapProductInfo);
                driver.close();
                driver.switchTo().window(tab1).navigate();
            }

            for (KakaoCategory kakaoCategory : kakaoCategoryList) {
                ExcelUtil.create(scrapProductInfoList.stream().filter(kakaoProductInfo -> kakaoProductInfo.getCategory().equals(kakaoCategory.getName())).collect(Collectors.toList()), kakaoCategory.getName().replaceAll("/", "_"));
            }
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        } finally {
            driver.quit();
        }
    }
}
