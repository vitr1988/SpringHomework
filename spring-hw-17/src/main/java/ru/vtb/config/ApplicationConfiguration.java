package ru.vtb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.client.RestTemplate;
import ru.vtb.model.Author;
import ru.vtb.model.Book;
import ru.vtb.model.Comment;
import ru.vtb.model.Genre;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return RepositoryRestConfigurer.withConfig(config -> {
            config.exposeIdsFor(Book.class);
            config.exposeIdsFor(Genre.class);
            config.exposeIdsFor(Author.class);
            config.exposeIdsFor(Comment.class);
        });
    }
}
