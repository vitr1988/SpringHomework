package ru.vtb.config.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.vtb.config.ApplicationProperties;

import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Component
public class DependentModuleHealthIndicator implements HealthIndicator {

    private final RestTemplate restTemplate;
    private final URI dependentModuleUrl;

    public DependentModuleHealthIndicator(RestTemplate restTemplate, ApplicationProperties applicationProperties) throws URISyntaxException {
        this.restTemplate = restTemplate;
        this.dependentModuleUrl = applicationProperties.getDependentModuleUrl().toURI();
    }

    @Override
    public Health health() {
        try {
            restTemplate.getForObject(dependentModuleUrl, String.class);
            return Health.up()
                    .withDetail("description", "dependent module is reachable").build();
        }
        catch (Exception e) {
            log.error("Exception happens", e);
            return Health.down()
                    .withDetail("description", "dependent module is unreachable")
                    .withDetail("reason", e.getLocalizedMessage()).build();
        }
    }
}
