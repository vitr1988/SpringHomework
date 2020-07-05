package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.dao.BookDao;
import ru.vtb.dao.dto.BookParamDto;
import ru.vtb.model.Book;
import ru.vtb.service.BookService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    @Override
    public List<Book> findAll() {
        return bookDao.findAll();
    }

    @Override
    public Optional<Book> getById(long bookId) {
        return bookDao.getById(bookId);
    }

    @Override
    public List<Book> getByParams(@NotNull BookParamDto paramDto) {
        return bookDao.getByParams(paramDto);
    }

    @Override
    @Transactional
    public Book save(@Valid Book book) {
        return bookDao.save(book);
    }

    @Override
    @Transactional
    public void deleteById(long bookId) {
        bookDao.deleteById(bookId);
    }

    @Override
    @Transactional
    public void delete(@Valid Book book) {
        bookDao.delete(book);
    }
}
