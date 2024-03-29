package kr.co.kpcard.scraping.gifticon.service;

import kr.co.kpcard.scraping.common.constant.IssuerEnum;
import kr.co.kpcard.scraping.common.domain.ScrapProductInfo;
import kr.co.kpcard.scraping.common.dto.ScrapProductInfoDto;
import kr.co.kpcard.scraping.common.excel.ExcelService;
import kr.co.kpcard.scraping.common.repository.ScrapProductInfoRepository;
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
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GifticonService {

    private final WebDriver driver;
    private final ScrapProductInfoRepository scrapProductInfoRepository;
    private final GifticonBrandScraping gifticonBrandScraping;
    private final GifticonProductScraping gifticonProductScraping;
    private final GifticonProductInfoExtractScraping gifticonProductInfoExtractScraping;
    private final ExcelService excelService;

    public void scrap() {
        // 크롤링 시작
        try {
            // 초기 페이지 로딩
            driver.get(IssuerEnum.GIFTICON.getIssuerStartUrl());

            // 초기 페이지 로딩 시간 2초 정도 딜레이
            Thread.sleep(2000);

            List<String> brandUrlList = gifticonBrandScraping.scraping(driver);

            List<String> productUrlList = new LinkedList<>();

            for (String brandUrl : brandUrlList) {
//            for (String brandUrl : brandUrlList.subList(0, 1)) {
                ScrapUtilService.openNewTab(driver, brandUrl);
                String tab1 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ZERO);
                String tab2 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ONE);
                driver.switchTo().window(tab2).navigate();

                productUrlList.addAll(gifticonProductScraping.scraping(driver));

                driver.close();
                driver.switchTo().window(tab1).navigate();
            }

            List<ScrapProductInfoDto> scrapProductInfoDtoList = new LinkedList<>();

            for (String productUrl : productUrlList) {
//            for (String productUrl : productUrlList.subList(0, 1)) {
                ScrapUtilService.openNewTab(driver, productUrl);
                String tab1 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ZERO);
                String tab2 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ONE);
                driver.switchTo().window(tab2).navigate();

                scrapProductInfoDtoList.add(gifticonProductInfoExtractScraping.scraping(driver));

                driver.close();
                driver.switchTo().window(tab1).navigate();
            }

//            scrapProductInfoRepository.saveAll(scrapProductInfoList);

            excelService.create(scrapProductInfoDtoList, IssuerEnum.GIFTICON.getIssuerDesc());
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        }
    }
}
