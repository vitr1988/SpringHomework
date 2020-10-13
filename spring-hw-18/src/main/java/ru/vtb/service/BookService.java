package ru.vtb.service;

import org.springframework.data.domain.Pageable;
import ru.vtb.dto.BookDto;
import ru.vtb.dto.PageDto;
import ru.vtb.model.Book;
import ru.vtb.repository.dto.BookParamDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDto> findAll();
    PageDto<BookDto> getPage(Pageable pageable);
    Optional<BookDto> getById(long bookId);
    List<BookDto> getByParams(@NotNull BookParamDto paramDto);
    BookDto save(@Valid BookDto book);
    BookDto partialSave(@Valid BookDto book);
    void deleteById(long bookId);
    void delete(@Valid Book book);
}
