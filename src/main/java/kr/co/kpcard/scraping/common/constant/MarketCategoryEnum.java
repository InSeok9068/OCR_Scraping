package kr.co.kpcard.scraping.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Arrays;

@Getter
@ToString
@RequiredArgsConstructor
public enum MarketCategoryEnum {
    MARKET_CATEGORY_1("1", "즐겨찾는 판매자"),
    MARKET_CATEGORY_2("2", "상품권류"),
    MARKET_CATEGORY_3("3", "공연/영화/뷰티"),
    MARKET_CATEGORY_4("4", "버거/피자/외식"),
    MARKET_CATEGORY_5("5", "커피/음료/아이스크림"),
    MARKET_CATEGORY_6("6", "기타/할인쿠폰/실물"),
    MARKET_CATEGORY_7("7", "베이커리/도넛"),
    MARKET_CATEGORY_8("8", "편의점/마트");

    private final String code;
    private final String desc;

    public static MarketCategoryEnum findByCode(String code) {
        return Arrays.stream(values())
                .filter(marketCategoryEnum -> marketCategoryEnum.getCode().equals(code))
                .findAny()
                .orElse(null);
    }
}
