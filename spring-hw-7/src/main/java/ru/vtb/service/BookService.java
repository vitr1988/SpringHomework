package ru.vtb.service;

import ru.vtb.repository.dto.BookParamDto;
import ru.vtb.model.Book;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();
    Optional<Book> getById(long bookId);
    List<Book> getByParams(@NotNull BookParamDto paramDto);
    Book save(@Valid Book book);
    void deleteById(long bookId);
    void delete(@Valid Book book);
}
