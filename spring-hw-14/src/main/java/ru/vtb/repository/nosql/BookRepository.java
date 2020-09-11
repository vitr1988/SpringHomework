package ru.vtb.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.vtb.model.nosql.Book;

public interface BookRepository extends MongoRepository<Book, String> {
}