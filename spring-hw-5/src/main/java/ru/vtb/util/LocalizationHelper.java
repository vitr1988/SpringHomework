package ru.vtb.util;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.vtb.config.ApplicationProperties;

import java.util.Locale;

@Component
public class LocalizationHelper {

    private final Locale locale;
    private final MessageSource messageSource;

    LocalizationHelper(ApplicationProperties properties, MessageSource messageSource) {
        this.locale = properties.getLocale();
        this.messageSource = messageSource;
    }

    public String localize(String key, Object... params) {
        return messageSource.getMessage(key, params, locale);
    }
}
