package ru.vtb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import ru.vtb.model.Book;

public interface BookRepository extends MongoRepository<Book, String> {
}