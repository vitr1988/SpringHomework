package ru.vtb.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class ApplicationConfiguration {

    @Bean
    public MessageSource messageSource(ApplicationProperties properties) {
        var ms = new ReloadableResourceBundleMessageSource();
        ms.setDefaultEncoding(UTF_8.displayName());
        ms.setBasename(properties.getResourceBaseName());
        return ms;
    }
}
