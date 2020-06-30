package ru.vtb.dao;

import ru.vtb.model.Author;
import ru.vtb.model.Genre;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    List<Author> findAll();
    Optional<Author> getById(long authorId);
    long create(@Valid Author author);
    void update(@Valid Author author);
    void deleteById(long authorId);
}
