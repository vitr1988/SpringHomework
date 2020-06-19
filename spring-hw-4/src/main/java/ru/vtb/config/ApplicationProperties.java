package ru.vtb.config;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.core.io.Resource;

import java.util.Locale;

@Value
@ConstructorBinding
@ConfigurationProperties("application")
public class ApplicationProperties {

    Locale locale;

    PollProperties poll;

    @Value
    @ConfigurationProperties("poll")
    public static class PollProperties {

        private Resource path;

        private Integer minQuestions;
    }
}
