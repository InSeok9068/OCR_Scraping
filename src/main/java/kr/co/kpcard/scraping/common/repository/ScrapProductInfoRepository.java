package kr.co.kpcard.scraping.common.repository;

import kr.co.kpcard.scraping.common.domain.ScrapProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapProductInfoRepository extends JpaRepository<ScrapProductInfo, Long> {
}