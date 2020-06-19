package ru.vtb.config;

import org.jline.utils.AttributedString;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;
import ru.vtb.util.LocalizationHelper;

import static org.jline.utils.AttributedStyle.DEFAULT;

@Configuration
@EnableConfigurationProperties(ApplicationProperties.class)
public class ApplicationConfiguration {

    @Bean
    public PromptProvider promptProvider(LocalizationHelper localizationHelper) {
        return () -> new AttributedString(localizationHelper.localize("intro") + " > ", DEFAULT);
    }
}
