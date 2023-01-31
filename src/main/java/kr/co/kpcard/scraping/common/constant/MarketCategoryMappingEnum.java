package kr.co.kpcard.scraping.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public enum MarketCategoryMappingEnum {
    MARKET_CATEGORY_1_MAPPING(MarketCategoryEnum.MARKET_CATEGORY_1, List.of("")),
    MARKET_CATEGORY_2_MAPPING(MarketCategoryEnum.MARKET_CATEGORY_2, List.of("")),
    MARKET_CATEGORY_3_MAPPING(MarketCategoryEnum.MARKET_CATEGORY_3, List.of("")),
    MARKET_CATEGORY_4_MAPPING(MarketCategoryEnum.MARKET_CATEGORY_4, List.of("구이/족발", "버거/피자", "분식/죽/도시락", "치킨", "패밀리/호텔뷔페", "퓨전/외국/펍", "한식/중식/일식")),
    MARKET_CATEGORY_5_MAPPING(MarketCategoryEnum.MARKET_CATEGORY_5, List.of("카페", "아이스크림/빙수")),
    MARKET_CATEGORY_6_MAPPING(MarketCategoryEnum.MARKET_CATEGORY_6, List.of("")),
    MARKET_CATEGORY_7_MAPPING(MarketCategoryEnum.MARKET_CATEGORY_7, List.of("베이커리/도넛/떡")),
    MARKET_CATEGORY_8_MAPPING(MarketCategoryEnum.MARKET_CATEGORY_8, List.of("편의점"));

    private final MarketCategoryEnum marketCategoryEnum;
    private final List<String> mappingList;

    public static MarketCategoryMappingEnum findByMappingCode(String mappingCode) {
        return Arrays.stream(values())
                .filter(marketCategoryMappingEnum -> marketCategoryMappingEnum.getMappingList().contains(mappingCode))
                .findAny()
                .orElse(null);
    }
}
