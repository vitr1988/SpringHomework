package ru.vtb.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import liquibase.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.dto.BookDto;
import ru.vtb.dto.PageDto;
import ru.vtb.mapper.BookMapper;
import ru.vtb.model.Book;
import ru.vtb.repository.AuthorRepository;
import ru.vtb.repository.BookRepository;
import ru.vtb.repository.GenreRepository;
import ru.vtb.repository.dto.BookParamDto;
import ru.vtb.security.Authorities;
import ru.vtb.service.BookService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    private final MutableAclService mutableAclService;
    private final Authorities authorities;

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(commandKey="findAllBooks", fallbackMethod="fallbackBooks")
    public List<BookDto> findAll() {
        return bookMapper.toDtos(bookRepository.findAll());
    }

    public List<BookDto> fallbackBooks() {
        return Arrays.asList(BookDto.NO_BOOK);
    }

    @Override
    @Transactional(readOnly = true)
    public PageDto<BookDto> getPage(Pageable pageable) {
        Page<Book> currentPage = bookRepository.findAll(pageable);
        return new PageDto<>(bookMapper.toDtos(currentPage.getContent()),
                currentPage.getNumber(),
                currentPage.getTotalPages(),
                currentPage.hasNext(),
                currentPage.hasPrevious(),
                authorities.isAdmin());
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(commandProperties= {
        @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000")
    })
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
        boolean newBook = bookId == 0;
        Book editedBook = newBook ? new Book() : bookRepository.getOne(bookId);
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
        BookDto dto = bookMapper.toDto(bookRepository.save(editedBook));
        if (newBook) {
            createAclEntry(dto.getId());
        }
        return dto;
    }

    @Override
    @Transactional
    public void deleteById(long bookId) {
        bookRepository.delete((Book) Hibernate.unproxy(bookRepository.getOne(bookId)));
    }

    @Override
    @Transactional
    public void delete(@Valid Book book) {
        bookRepository.delete(book);
    }

    private void createAclEntry(long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Sid owner = new PrincipalSid(authentication);
        ObjectIdentity oid = new ObjectIdentityImpl(Book.class, bookId);

        MutableAcl acl = mutableAclService.createAcl(oid);
        acl.setOwner(owner);
        acl.insertAce(acl.getEntries().size(), BasePermission.DELETE, owner, true);
        acl.setEntriesInheriting(false);

        mutableAclService.updateAcl(acl);
    }
}
