package ru.vtb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.vtb.model.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
