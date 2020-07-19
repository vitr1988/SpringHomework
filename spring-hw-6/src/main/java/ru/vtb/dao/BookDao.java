package ru.vtb.dao;

import ru.vtb.dao.dto.BookParamDto;
import ru.vtb.model.Book;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface BookDao {
    List<Book> findAll();
    Optional<Book> getById(long bookId);
    List<Book> getByParams(@NotNull BookParamDto paramDto);
    Book save(@Valid Book book);
    void deleteById(long bookId);
    void delete(@Valid Book book);
}
