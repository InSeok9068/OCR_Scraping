package kr.co.kpcard.scraping.common.repository;

import kr.co.kpcard.scraping.common.domain.ScrapFailInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapFailInfoRepository extends JpaRepository<ScrapFailInfo, Long> {
}
