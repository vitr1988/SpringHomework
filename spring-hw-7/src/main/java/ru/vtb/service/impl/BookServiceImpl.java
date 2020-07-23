package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.model.Book;
import ru.vtb.repository.BookRepository;
import ru.vtb.repository.dto.BookParamDto;
import ru.vtb.service.BookService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> getById(long bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getByParams(@NotNull BookParamDto paramDto) {
        return bookRepository.getByParams(paramDto);
    }

    @Override
    @Transactional
    public Book save(@Valid Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteById(long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    @Transactional
    public void delete(@Valid Book book) {
        bookRepository.delete(book);
    }
}
