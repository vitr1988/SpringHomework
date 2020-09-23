package ru.otus.spring.integration.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.integration.domain.Content;
import ru.otus.spring.integration.domain.Information;
import ru.otus.spring.integration.util.ContentExtractor;

@Service
public class NewsService {

    public Information process(Content content) throws Exception {
        return new Information(ContentExtractor.getContentBy(content.getHref(), "article__text").text());
    }
}
