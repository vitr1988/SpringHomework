package ru.otus.spring.integration.util;

import lombok.experimental.UtilityClass;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;

@UtilityClass
public class ContentExtractor {

    public Elements getContentBy(String href, String className) throws IOException {
        return Jsoup.connect(href).get().getElementsByClass(className);
    }
}
