package ru.vtb.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.vtb.model.Book;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}