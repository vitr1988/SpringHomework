package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.dto.BookDto;
import ru.vtb.dto.PageDto;
import ru.vtb.mapper.BookMapper;
import ru.vtb.model.Book;
import ru.vtb.repository.AuthorRepository;
import ru.vtb.repository.BookRepository;
import ru.vtb.repository.GenreRepository;
import ru.vtb.service.BookService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        return bookMapper.toDtos(bookRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public PageDto<BookDto> getPage(Pageable pageable) {
        Page<Book> currentPage = bookRepository.findAll(pageable);
        return new PageDto<>(bookMapper.toDtos(currentPage.getContent()),
                currentPage.getNumber(),
                currentPage.getTotalPages(),
                currentPage.hasNext(),
                currentPage.hasPrevious());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> getById(String bookId) {
        return bookMapper.toOptionalDto(bookRepository.findById(bookId));
    }

    @Override
    @Transactional
    public BookDto save(@Valid BookDto book) {
        Book bookEntity = bookMapper.toEntity(book);
        authorRepository.findById(book.getAuthorId()).ifPresent(bookEntity::setAuthor);
        genreRepository.findById(book.getGenreCode()).ifPresent(bookEntity::setGenre);
        return bookMapper.toDto(bookRepository.save(bookEntity));
    }

    @Override
    @Transactional
    public void partialSave(@Valid BookDto book) {
        bookRepository.findById(book.getId()).ifPresent(bookEntity -> {
            bookEntity.setIsbn(book.getIsbn());
            bookEntity.setName(book.getName());
            bookRepository.save(bookEntity);
        });
    }

    @Override
    @Transactional
    public void deleteById(String bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    @Transactional
    public void delete(@Valid BookDto book) {
        bookRepository.delete(bookMapper.toEntity(book));
    }
}
