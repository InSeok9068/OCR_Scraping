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
public class ScrapFailInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long seqNo;
    private String url;
    
    @CreatedDate
    private LocalDateTime createdDate;
}
