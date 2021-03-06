package kr.co.kpcard.scraping.kakao.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class KakaoProductInfo {
    private final String brand;
    private final String title;
    private final String price;
    private final String content;
    private final String couponType;
    private final String imageFileName;
    @Setter
    private String categoryName;
}
