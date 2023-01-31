package kr.co.kpcard.scraping.common.dto;

import kr.co.kpcard.scraping.common.constant.CouponTypeEnum;
import kr.co.kpcard.scraping.common.constant.MarketCategoryEnum;
import kr.co.kpcard.scraping.common.util.ScrapUtilService;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class ScrapProductInfoDto {
    private long seqNo;
    private String issuer;
    private String title;
    private String brand;
    private String searchTitle;
    private String searchBrand;
    private String categoryCode;
    private String categoryDesc;
    private String couponTypeCode;
    private String couponTypeDesc;
    private int price;
    private String content;
    private String image;

    public void setTitle(String title) {
        this.title = title;
        this.searchTitle = ScrapUtilService.transferOnlyText(title);
    }

    public void setBrand(String brand) {
        this.brand = brand;
        this.searchBrand = ScrapUtilService.transferOnlyText(brand);
    }

    public void setPrice(String price) {
        this.price = Integer.parseInt(StringUtils.defaultString(ScrapUtilService.transferOnlyNumber(price), "0"));
    }

    public void setMarketCategoryEnum(MarketCategoryEnum marketCategoryEnum) {
        this.categoryCode = marketCategoryEnum.getCode();
        this.categoryDesc = marketCategoryEnum.getDesc();
    }

    public void setCouponTypeEnum(CouponTypeEnum couponTypeEnwum) {
        this.couponTypeCode = couponTypeEnwum.getCode();
        this.couponTypeDesc = couponTypeEnwum.getDesc();
    }
}
