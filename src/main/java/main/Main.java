package main;

import lombok.extern.slf4j.Slf4j;
import main.domain.Brand;
import main.domain.Category;
import main.domain.Product;
import main.domain.ProductInfo;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;
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
            List<Category> categoryList = CategoryScraping.scraping(driver);

            List<Brand> brandList = new ArrayList<>();

//            for (Category category : categoryList) {
            for (Category category : categoryList) {
                ScrapingUtil.openNewTab(driver, category.getUrl());
                String tab1 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ZERO);
                String tab2 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ONE);
                driver.switchTo().window(tab2).navigate();

                List<Brand> brands = BrandScraping.scraping(driver);
                brands.forEach(brand -> brand.setCategoryName(category.getName()));

                brandList.addAll(brands);
                driver.close();
                driver.switchTo().window(tab1).navigate();
            }

            brandList = brandList.stream().distinct().collect(Collectors.toList());

            List<Product> productList = new ArrayList<>();

//            for (Brand brand : brandList) {
            for (Brand brand : brandList) {
                ScrapingUtil.openNewTab(driver, brand.getUrl());
                String tab1 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ZERO);
                String tab2 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ONE);
                driver.switchTo().window(tab2).navigate();

                List<Product> products = ProductScraping.scraping(driver);
                products.forEach(product -> product.setCategoryName(brand.getCategoryName()));

                productList.addAll(products);
                driver.close();
                driver.switchTo().window(tab1).navigate();
            }

            List<ProductInfo> productInfoList = new ArrayList<>();

            for (Product product : productList) {
                ScrapingUtil.openNewTab(driver, product.getUrl());
                String tab1 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ZERO);
                String tab2 = new ArrayList<>(driver.getWindowHandles()).get(NumberUtils.INTEGER_ONE);
                driver.switchTo().window(tab2).navigate();

                ProductInfo productInfo = ProductInfoExtractScraping.scraping(driver);
                productInfo.setCategoryName(product.getCategoryName());

                productInfoList.add(productInfo);
                driver.close();
                driver.switchTo().window(tab1).navigate();
            }

            for (Category category : categoryList) {
                Excel.create(productInfoList.stream().filter(productInfo -> productInfo.getCategoryName().equals(category.getName())).collect(Collectors.toList()), category.getName().replaceAll("/", "_"));
            }
        } catch (Exception exception) {
            log.error(ExceptionUtils.getStackTrace(exception));
        } finally {
            driver.quit();
        }
    }
}
