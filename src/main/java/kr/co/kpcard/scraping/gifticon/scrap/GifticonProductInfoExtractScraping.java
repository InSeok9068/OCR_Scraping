package kr.co.kpcard.scraping.gifticon.scrap;

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
public class GifticonProductInfoExtractScraping {

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
            title = driver.findElement(By.className("pdt_name")).getText();
        } catch (Exception exception) {
            isError = true;
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        try {
            brand = driver.findElement(By.className("shopna")).getText();
            brand = brand.substring(0, brand.indexOf("매장교환") - 1);
        } catch (Exception exception) {
            isError = true;
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        try {
            price = driver.findElement(By.className("cost")).getText();
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
            StringBuilder sb = new StringBuilder();
            content = sb.append(driver.findElement(By.id("con1")).getText())
                    .append(driver.findElement(By.id("con2")).getText())
                    .toString();
        } catch (Exception exception) {
            isError = true;
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        try {
            imageSrc = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[2]/div[1]/div/span/img")).getAttribute("src");
        } catch (Exception exception) {
            isError = true;
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        if (isError) {
            scrapFailInfoRepository.save(ScrapFailInfo.builder()
                    .url(driver.getCurrentUrl())
                    .build());
        }

//        fileName = scrapUtilService.saveImage(imageSrc, IssuerEnum.GIFTICON.getIssuerCode() + "_");

        ScrapProductInfoDto scrapProductInfoDto = new ScrapProductInfoDto();
        scrapProductInfoDto.setSeqNo(ScrapUtilService.getSeqNo());
        scrapProductInfoDto.setIssuer(IssuerEnum.GIFTICON.getIssuerCode());
        scrapProductInfoDto.setTitle(title);
        scrapProductInfoDto.setBrand(brand);
        scrapProductInfoDto.setCouponTypeEnum(CouponTypeMappingEnum.findByMappingCode(couponType).getCouponTypeEnum());
        scrapProductInfoDto.setPrice(price);
        scrapProductInfoDto.setContent(content);
        scrapProductInfoDto.setImageUrl(imageSrc);

        return scrapProductInfoDto;
    }
}
