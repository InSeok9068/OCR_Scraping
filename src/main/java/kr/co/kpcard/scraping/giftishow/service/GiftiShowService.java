package kr.co.kpcard.scraping.giftishow.service;

import kr.co.kpcard.scraping.common.constant.IssuerEnum;
import kr.co.kpcard.scraping.common.domain.ScrapProductInfo;
import kr.co.kpcard.scraping.common.excel.ExcelService;
import kr.co.kpcard.scraping.common.repository.ScrapProductInfoRepository;
import kr.co.kpcard.scraping.common.util.ScrapUtilService;
import kr.co.kpcard.scraping.giftishow.scrap.GiftiShowBrandScraping;
import kr.co.kpcard.scraping.giftishow.scrap.GiftiShowProductInfoExtractScraping;
import kr.co.kpcard.scraping.giftishow.scrap.GiftiShowProductScraping;
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
public class GiftiShowService {

    private final WebDriver driver;
    private final ScrapProductInfoRepository scrapProductInfoRepository;
    private final GiftiShowBrandScraping giftiShowBrandScraping;
    private final GiftiShowProductScraping giftiShowProductScraping;
    private final GiftiShowProductInfoExtractScraping giftiShowProductInfoExtractScraping;
    private final ExcelService excelService;

    public void scrap() {
        // 크롤링 시작
        try {
            // 초기 페이지 로딩
            driver.get(IssuerEnum.GIFTISHOW.getIssuerStartUrl());

            // 초기 페이지 로딩 시간 2초 정도 딜레이
            Thread.sleep(2000);

//            List<String> brandUrlList = giftiShowBrandScraping.scraping(driver);

            List<String> productUrlList = new ArrayList<>();

//            for (String brandUrl : brandUrlList) {
//            for (String brandUrl : brandUrlList.subList(0, 1)) {
//                ScrapUtilService.openNewTab(driver, brandUrl);
//                String tab1 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ZERO);
//                String tab2 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ONE);
//                driver.switchTo().window(tab2).navigate();
//
//                productUrlList.addAll(giftiShowProductScraping.scraping(driver));
//
//                driver.close();
//                driver.switchTo().window(tab1).navigate();
//            }

            productUrlList.addAll(giftiShowProductScraping.scraping(driver));

            List<ScrapProductInfo> scrapProductInfoList = new ArrayList<>();

            for (String productUrl : productUrlList) {
//            for (String productUrl : productUrlList.subList(0, 1)) {
                ScrapUtilService.openNewTab(driver, productUrl);
                String tab1 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ZERO);
                String tab2 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ONE);
                driver.switchTo().window(tab2).navigate();

                scrapProductInfoList.add(giftiShowProductInfoExtractScraping.scraping(driver));

                driver.close();
                driver.switchTo().window(tab1).navigate();
            }

//            scrapProductInfoRepository.saveAll(scrapProductInfoList);

            excelService.create(scrapProductInfoList, IssuerEnum.GIFTISHOW.getIssuerDesc());
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }
    }
}
