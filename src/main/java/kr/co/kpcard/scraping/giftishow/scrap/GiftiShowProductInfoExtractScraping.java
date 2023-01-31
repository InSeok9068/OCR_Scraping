package kr.co.kpcard.scraping.giftishow.scrap;

import kr.co.kpcard.scraping.common.constant.CouponTypeMappingEnum;
import kr.co.kpcard.scraping.common.constant.IssuerEnum;
import kr.co.kpcard.scraping.common.domain.ScrapFailInfo;
import kr.co.kpcard.scraping.common.domain.ScrapProductInfo;
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
public class GiftiShowProductInfoExtractScraping {

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
            title = driver.findElement(By.className("itemNm")).getText();
        } catch (Exception exception) {
            isError = true;
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        try {
            int pTagSize = driver.findElements(By.xpath("/html/body/div[3]/div/div[1]/div[1]/div/div[2]/div[1]/p")).size();

            if (pTagSize == 4) {
                brand = driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div[1]/div/div[2]/div[1]/p[3]")).getText();
            } else {
                brand = driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div[1]/div/div[2]/div[1]/p[2]")).getText();
            }
            brand = brand.substring(brand.indexOf(":") + 2);
        } catch (Exception exception) {
            try {
                int pTagSize = driver.findElements(By.xpath("/html/body/div[4]/div/div[1]/div[1]/div/div[2]/div[1]/p")).size();

                if (pTagSize == 4) {
                    brand = driver.findElement(By.xpath("/html/body/div[4]/div/div[1]/div[1]/div/div[2]/div[1]/p[3]")).getText();
                } else {
                    brand = driver.findElement(By.xpath("/html/body/div[4]/div/div[1]/div[1]/div/div[2]/div[1]/p[2]")).getText();
                }
                brand = brand.substring(brand.indexOf(":") + 2);
            } catch (Exception exception1) {
                isError = true;
                log.error(ExceptionUtils.getStackTrace(exception));
            }
        }

        try {
            price = driver.findElement(By.className("price")).getText();
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
            content = sb.append(driver.findElement(By.className("txtCon")).getText())
                    .append(driver.findElement(By.className("pnotandum")).getText())
                    .toString();
        } catch (Exception exception) {
            isError = true;
            log.error(ExceptionUtils.getStackTrace(exception));
        }

        try {
            imageSrc = driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div[1]/div/div[1]/img")).getAttribute("src");
        } catch (Exception exception) {
            try {
                imageSrc = driver.findElement(By.xpath("/html/body/div[4]/div/div[1]/div[1]/div/div[1]/img")).getAttribute("src");
            } catch (Exception exception1) {
                isError = true;
                log.error(ExceptionUtils.getStackTrace(exception));
            }
        }

        if (isError) {
            scrapFailInfoRepository.save(ScrapFailInfo.builder()
                    .url(driver.getCurrentUrl())
                    .build());
        }

        fileName = scrapUtilService.saveImage(imageSrc, IssuerEnum.GIFTISHOW.getIssuerCode() + "_");

        ScrapProductInfoDto scrapProductInfoDto = new ScrapProductInfoDto();
        scrapProductInfoDto.setSeqNo(ScrapUtilService.getSeqNo());
        scrapProductInfoDto.setIssuer(IssuerEnum.GIFTISHOW.getIssuerCode());
        scrapProductInfoDto.setTitle(title);
        scrapProductInfoDto.setBrand(brand);
        scrapProductInfoDto.setCouponTypeEnum(CouponTypeMappingEnum.findByMappingCode(couponType).getCouponTypeEnum());
        scrapProductInfoDto.setPrice(price);
        scrapProductInfoDto.setContent(content);
        scrapProductInfoDto.setImage(fileName);

        return scrapProductInfoDto;
    }
}
