package kr.co.kpcard.scraping.common.config;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebDriver;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShutdownEvent implements ApplicationListener<ContextClosedEvent> {

    private final WebDriver driver;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        driver.close();
    }
}
