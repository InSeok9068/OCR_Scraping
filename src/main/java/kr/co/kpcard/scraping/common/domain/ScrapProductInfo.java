package kr.co.kpcard.scraping.common.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    private LocalDateTime createdDate;
}
