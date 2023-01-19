package kr.co.kpcard.scraping.common.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ScrapFailInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seqNo;
    private String url;
}
