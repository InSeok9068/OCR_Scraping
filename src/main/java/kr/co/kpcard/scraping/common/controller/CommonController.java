package kr.co.kpcard.scraping.common.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.kpcard.scraping.gifticon.service.GifticonService;
import kr.co.kpcard.scraping.giftishow.service.GiftiShowService;
import kr.co.kpcard.scraping.kakao.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"OCR 상품 정보 스크래핑"})
@RestController
@RequiredArgsConstructor
public class CommonController {

    private final KakaoService kakaoService;
    private final GifticonService gifticonService;
    private final GiftiShowService giftiShowService;

    @ApiOperation(value = "ALL 스크래핑")
    @PostMapping(value = "/all")
    public void all() {
        kakaoService.scrap();
        gifticonService.scrap();
        giftiShowService.scrap();
    }

    @ApiOperation(value = "카카오 스크래핑")
    @PostMapping(value = "/kakao")
    public void kakao() {
        kakaoService.scrap();
    }

    @ApiOperation(value = "기프티콘 스크래핑")
    @PostMapping(value = "/gifticon")
    public void gifticon() {
        gifticonService.scrap();
    }

    @ApiOperation(value = "기프티쇼 스크래핑")
    @PostMapping(value = "/giftiShow")
    public void giftiShow() {
        giftiShowService.scrap();
    }
}
