package kr.co.kpcard.scraping.kakao.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoCategory {
    private final String name;
    private final String url;
}
