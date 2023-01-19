package kr.co.kpcard.scraping.common.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScrapProductInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seqNo;
    private String issuer;
    private String title;
    private String brand;
    private String subBrand;
    @Setter
    private String category;
    private String couponType;
    private String price;
    @Lob
    @Column
    private String content;
    private String image;
}
