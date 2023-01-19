package kr.co.kpcard.scraping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ScrapingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScrapingApplication.class, args);
    }

}
