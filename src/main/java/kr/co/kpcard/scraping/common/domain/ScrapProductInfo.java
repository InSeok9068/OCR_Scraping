package kr.co.kpcard.scraping.common.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ScrapProductInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seqNo;
    private String issuer;
    private String title;
    private String brand;
    private String subBrand;
    private String category;
    private String couponType;
    private String price;
    @Lob
    @Column
    private String content;
    private String image;

    @Builder
    public ScrapProductInfo(Long seqNo, String issuer, String title, String brand, String subBrand, String category, String couponType, String price, String content, String image) {
        this.seqNo = seqNo;
        this.issuer = issuer;
        this.title = title;
        this.brand = brand;
        this.subBrand = subBrand;
        this.category = category;
        this.couponType = couponType;
        this.price = price;
        this.content = content;
        this.image = image;
    }
}
