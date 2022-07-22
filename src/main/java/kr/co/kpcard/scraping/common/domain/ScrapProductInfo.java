package kr.co.kpcard.scraping.common.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ScrapProductInfo {
    private final String issuer;
    private final String title;
    private final String brand;
    private final String subBrand;
    @Setter
    private String category;
    private final String couponType;
    private final String price;
    private final String content;
    private final String image;
}
