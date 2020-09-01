package ru.vtb.service;

import org.springframework.data.domain.Pageable;
import ru.vtb.dto.BookDto;
import ru.vtb.dto.PageDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDto> findAll();
    PageDto<BookDto> getPage(Pageable pageable);
    Optional<BookDto> getById(String bookId);
    BookDto save(@Valid BookDto book);
    void partialSave(@Valid BookDto book);
    void deleteById(String bookId);
    void delete(@Valid BookDto book);
}
