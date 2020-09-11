package ru.vtb.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.vtb.model.nosql.Author;

public interface AuthorRepository extends MongoRepository<Author, String> {
}
