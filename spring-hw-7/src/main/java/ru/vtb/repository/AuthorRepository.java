package ru.vtb.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vtb.model.Author;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
