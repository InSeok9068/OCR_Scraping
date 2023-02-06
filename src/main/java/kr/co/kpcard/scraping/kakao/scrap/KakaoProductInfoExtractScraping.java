package kr.co.kpcard.scraping.kakao.scrap;

import kr.co.kpcard.scraping.common.constant.CouponTypeMappingEnum;
import kr.co.kpcard.scraping.common.constant.IssuerEnum;
import kr.co.kpcard.scraping.common.domain.ScrapFailInfo;
import kr.co.kpcard.scraping.common.dto.ScrapProductInfoDto;
import kr.co.kpcard.scraping.common.repository.ScrapFailInfoRepository;
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
public class KakaoProductInfoExtractScraping {

    private final ScrapUtilService scrapUtilService;
    private final ScrapFailInfoRepository scrapFailInfoRepository;

    public ScrapProductInfoDto scraping(WebDriver driver) throws IOException {
        String title = StringUtils.EMPTY;
        String brand = StringUtils.EMPTY;
        String price = StringUtils.EMPTY;
        String content = StringUtils.EMPTY;
        String couponType = StringUtils.EMPTY;
        String imageSrc = StringUtils.EMPTY;
        String fileName;

        boolean isError = false;

        try {
            title = driver.findElement(By.className("tit_subject")).getText();
            title = title.trim();
        } catch (Exception exception) {
            isError = true;
            log.error(ExceptionUtils.getStackTrace(exception));
        }
        try {
            brand = driver.findElement(By.className("inner_shopname")).getText();
            brand = StringUtils.replace(brand, "바로가기", StringUtils.EMPTY);
            brand = brand.trim();
        } catch (Exception exception) {
            isError = true;
            log.error(ExceptionUtils.getStackTrace(exception));
        }
        try {
            price = driver.findElement(By.className("txt_total")).getText();
            price = StringUtils.replace(price, "원", StringUtils.EMPTY);
            price = price.trim();
        } catch (Exception exception) {
            isError = true;
            log.error(ExceptionUtils.getStackTrace(exception));
        }
        try {
            couponType = (title.contains("원권")) ? "금액권" : "교환권";
        } catch (Exception exception) {
            isError = true;
            log.error(ExceptionUtils.getStackTrace(exception));
        }
        try {
            content = driver.findElement(By.className("desc_explain")).getText();
        } catch (Exception exception) {
            isError = true;
            log.error(ExceptionUtils.getStackTrace(exception));
        }
        try {
            imageSrc = driver.findElement(By.xpath("/html/body/app-root/app-view-wrapper/div/div/main/article/app-home/div/app-main/div/div/div[1]/div/ngx-flicking/div/div/img")).getAttribute("src");
        } catch (Exception exception) {
            isError = true;
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        if (isError) {
            scrapFailInfoRepository.save(ScrapFailInfo.builder()
                    .url(driver.getCurrentUrl())
                    .build());
        }

//        fileName = scrapUtilService.saveImage(imageSrc, IssuerEnum.KAKAO.getIssuerCode() + "_");

        ScrapProductInfoDto scrapProductInfoDto = new ScrapProductInfoDto();
        scrapProductInfoDto.setSeqNo(ScrapUtilService.getSeqNo());
        scrapProductInfoDto.setIssuer(IssuerEnum.KAKAO.getIssuerCode());
        scrapProductInfoDto.setTitle(title);
        scrapProductInfoDto.setBrand(brand);
        scrapProductInfoDto.setCouponTypeEnum(CouponTypeMappingEnum.findByMappingCode(couponType).getCouponTypeEnum());
        scrapProductInfoDto.setPrice(price);
        scrapProductInfoDto.setContent(content);
        scrapProductInfoDto.setImageUrl(imageSrc);

        return scrapProductInfoDto;
    }
}
