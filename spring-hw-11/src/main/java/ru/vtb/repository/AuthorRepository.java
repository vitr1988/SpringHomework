package ru.vtb.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.vtb.model.Author;

public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {
}
