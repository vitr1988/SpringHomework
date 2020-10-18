package ru.vtb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import ru.vtb.config.ApplicationProperties;

@EnableCircuitBreaker
@SpringBootApplication
@EntityScan("ru.vtb.model")
@EnableConfigurationProperties(ApplicationProperties.class)
public class Library {

    public static void main(String[] args) {
        SpringApplication.run(Library.class, args);
    }
}
