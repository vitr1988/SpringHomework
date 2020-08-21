package ru.vtb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.vtb.config.ApplicationProperties;
import ru.vtb.model.Book;
import ru.vtb.model.Genre;
import ru.vtb.repository.BookRepository;
import ru.vtb.repository.GenreRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@EnableConfigurationProperties(ApplicationProperties.class)
@SpringBootApplication(exclude = MongoAutoConfiguration.class)
public class Library {

    public static void main(String[] args) {
        SpringApplication.run(Library.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> composedRoutes(GenreRepository genreRepository, BookRepository bookRepository) {
        return route()
                .GET("/api/genres", accept(APPLICATION_JSON),
                        request ->  ok().contentType(APPLICATION_JSON).body(genreRepository.findAll(), Genre.class)
                )
                .DELETE("/api/genres/{code}",
                        request -> genreRepository.deleteById(request.pathVariable("code")).flatMap(v -> ok().build())
                )
                .GET("/api/books", accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON).body(bookRepository.findAll(), Genre.class)
                )
                .DELETE("/api/books/{id}",
                        request -> bookRepository.deleteById(request.pathVariable("id")).flatMap(v -> ok().build())
                ).build();
    }
}

