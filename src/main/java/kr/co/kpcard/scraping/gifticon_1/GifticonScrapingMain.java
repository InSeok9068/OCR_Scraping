package kr.co.kpcard.scraping.gifticon_1;

import kr.co.kpcard.scraping.common.ChromDriverStart;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Slf4j
public class GifticonScrapingMain {
    public static void main(String[] args) {
        final WebDriver driver = ChromDriverStart.createWebDriver(false);

        // 크롤링 시작
        try {
//            // 초기 페이지 로딩
//            driver.get("https://www.gifticon.com/shopping/shopping_brandshop.do");
//
//            // 초기 페이지 로딩 시간 2초 정도 딜레이
//            Thread.sleep(2000);
//
//            List<String> brandList = GiftiShowBrandScraping.scraping(driver);

            driver.get("https://www.gifticon.com/shopping/shopping_brandview.do?prodDispId=FS0028#giftreview_list");

            driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div/div[7]/div[2]/a[4]")).click();

        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        } finally {
            driver.quit();
        }
    }
}
