package ru.vtb.service;

import ru.vtb.model.Author;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * TODO: в дальнейшем сервисы будут работать не напрямую с Entity, а с проекциями или dto
 */
public interface AuthorService {
    List<Author> findAll();
    Optional<Author> getById(long authorId);
    Author save(@Valid Author author);
    void deleteById(long authorId);
    void delete(@Valid Author author);
}
