package kr.co.kpcard.scraping.common.controller;

import kr.co.kpcard.scraping.gifticon.service.GifticonService;
import kr.co.kpcard.scraping.giftishow.service.GiftiShowService;
import kr.co.kpcard.scraping.kakao.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommonController {

    private final KakaoService kakaoService;
    private final GifticonService gifticonService;
    private final GiftiShowService giftiShowService;

    @PostMapping(value = "/kakao")
    public void kakao() {
        kakaoService.scrap();
    }

    @PostMapping(value = "/gifticon")
    public void gifticon() {
        gifticonService.scrap();
    }

    @PostMapping(value = "/giftiShow")
    public void giftiShow() {
        giftiShowService.scrap();
    }
}
