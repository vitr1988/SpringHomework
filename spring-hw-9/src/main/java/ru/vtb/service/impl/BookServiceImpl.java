package ru.vtb.service.impl;

import liquibase.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.dto.BookDto;
import ru.vtb.dto.page.BookPageDto;
import ru.vtb.mapper.BookMapper;
import ru.vtb.model.Book;
import ru.vtb.repository.AuthorRepository;
import ru.vtb.repository.BookRepository;
import ru.vtb.repository.GenreRepository;
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
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        return bookMapper.toDtos(bookRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public BookPageDto getPage(Pageable pageable) {
        Page<Book> currentPage = bookRepository.findAll(pageable);
        return new BookPageDto(bookMapper.toDtos(currentPage.getContent()),
                currentPage.getNumber(),
                currentPage.getTotalPages(),
                currentPage.hasNext(),
                currentPage.hasPrevious());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> getById(long bookId) {
        return bookMapper.toOptionalDto(bookRepository.findById(bookId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getByParams(@NotNull BookParamDto paramDto) {
        return bookMapper.toDtos(bookRepository.getByParams(paramDto));
    }

    @Override
    @Transactional
    public BookDto save(@Valid BookDto book) {
        return bookMapper.toDto(bookRepository.save(bookMapper.toEntity(book)));
    }

    @Override
    @Transactional
    public BookDto partialSave(@Valid BookDto book) {
        long bookId = book.getId();
        Book editedBook = bookId == 0 ? new Book() : bookRepository.getOne(bookId);
        editedBook.setIsbn(book.getIsbn());
        editedBook.setName(book.getName());
        String genreCode = book.getGenreCode();
        if (StringUtils.isNotEmpty(genreCode)) {
            editedBook.setGenre(genreRepository.getOne(genreCode));
        }
        long authorId = book.getAuthorId();
        if (authorId != 0) {
            editedBook.setAuthor(authorRepository.getOne(authorId));
        }
        return bookMapper.toDto(bookRepository.save(editedBook));
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
