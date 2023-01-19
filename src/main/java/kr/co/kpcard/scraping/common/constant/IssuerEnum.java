package kr.co.kpcard.scraping.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IssuerEnum {
    KAKAO("KAKAO", "카카오", "https://gift.kakao.com/brand/category/91/subcategory/146"),
    GIFTICON("GIFTICON", "기프티콘", "https://www.gifticon.com/shopping/shopping_brandshop.do"),
    GIFTISHOW("GIFTISHOW", "기프티쇼", "https://www.giftishow.com/brand/brandList.mhows");

    private final String issuerCode;
    private final String issuerDesc;
    private final String issuerStartUrl;
}
