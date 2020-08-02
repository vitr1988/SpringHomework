package ru.vtb.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("ru.vtb.model")
@EnableConfigurationProperties(ApplicationProperties.class)
public class ApplicationConfiguration {

}
