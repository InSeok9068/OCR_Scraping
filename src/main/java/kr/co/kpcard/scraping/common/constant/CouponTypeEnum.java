package kr.co.kpcard.scraping.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Arrays;

@Getter
@ToString
@RequiredArgsConstructor
public enum CouponTypeEnum {
    COUPON_TYPE_1("0", "미분류"),
    COUPON_TYPE_2("1", "기프티콘"),
    COUPON_TYPE_3("2", "금액권"),
    COUPON_TYPE_4("3", "할인/기타"),
    COUPON_TYPE_5("4", "상품권");

    private final String code;
    private final String desc;

    public static CouponTypeEnum findByCode(String code) {
        return Arrays.stream(values())
                .filter(couponTypeEnum -> couponTypeEnum.getCode().equals(code))
                .findAny()
                .orElse(null);
    }
}
