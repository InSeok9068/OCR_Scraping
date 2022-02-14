package kr.co.kpcard.scraping.kakao.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class KakaoProduct {
    private final String url;
    @Setter
    private String categoryName;
}
