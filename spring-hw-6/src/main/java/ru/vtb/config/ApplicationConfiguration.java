package ru.vtb.config;

import lombok.SneakyThrows;
import org.jline.utils.AttributedString;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;
import ru.vtb.util.LocalizationHelper;

import static org.jline.utils.AttributedStyle.DEFAULT;

@Configuration
@EntityScan("ru.vtb.model")
@EnableConfigurationProperties(ApplicationProperties.class)
public class ApplicationConfiguration {

    /**
     * Parametrized prompter for Spring shell
     *
     * @param localizationHelper        localization helper bean
     * @return provider for prompt
     */
    @Bean
    @SneakyThrows
    public PromptProvider promptProvider(LocalizationHelper localizationHelper) {
        return () -> new AttributedString(localizationHelper.localize("intro"), DEFAULT);
    }
}
