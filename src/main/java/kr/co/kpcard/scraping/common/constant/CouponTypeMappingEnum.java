package kr.co.kpcard.scraping.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
public enum CouponTypeMappingEnum {
    COUPON_TYPE_1_MAPPING(CouponTypeEnum.COUPON_TYPE_1, List.of("")),
    COUPON_TYPE_2_MAPPING(CouponTypeEnum.COUPON_TYPE_2, List.of("교환권")),
    COUPON_TYPE_3_MAPPING(CouponTypeEnum.COUPON_TYPE_3, List.of("금액권")),
    COUPON_TYPE_4_MAPPING(CouponTypeEnum.COUPON_TYPE_4, List.of("")),
    COUPON_TYPE_5_MAPPING(CouponTypeEnum.COUPON_TYPE_5, List.of(""));

    private final CouponTypeEnum couponTypeEnum;
    private final List<String> mappingList;

    public static CouponTypeMappingEnum findByMappingCode(String mappingCode) {
        return Arrays.stream(values())
                .filter(couponTypeMappingEnum -> couponTypeMappingEnum.getMappingList().contains(mappingCode))
                .findAny()
                .orElse(null);
    }
}
