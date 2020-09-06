package ru.vtb.config;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.util.Locale;

@Value
@ConstructorBinding
@ConfigurationProperties("application")
public class ApplicationProperties {

    private Locale locale;
}
