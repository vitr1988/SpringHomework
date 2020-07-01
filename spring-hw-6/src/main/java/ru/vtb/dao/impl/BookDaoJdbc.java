package ru.vtb.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import ru.vtb.dao.BookDao;
import ru.vtb.dao.dto.BookParamDto;
import ru.vtb.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Validated
@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> findAll() {
        return em.createQuery("select b FROM Book b " +
                "join fetch Genre g on b.genre.code = g.code " +
                "join fetch Author a on b.author.id = a.id").getResultList();
    }

    @Override
    public Optional<Book> getById(long bookId) {
        return Optional.ofNullable(em.find(Book.class, bookId));
    }

    @Override
    public List<Book> getByParams(@NotNull BookParamDto paramDto) {
        return em.createQuery("select b from Book b " +
                "join fetch Genre g on b.genre.code = g.code " +
                "join fetch Author a on b.author.id = a.id " +
                "where (:bookId is null or b.id = :bookId) and " +
                "(:bookIsbn is null or b.isbn = :bookIsbn) and " +
                "(:bookName is null or lower(b.name) like CONCAT('%', :bookName ,'%')) and " +
                "(:authorId is null or a.id = :authorId) and " +
                "(:genreCode is null or g.code = :genreCode) ")
                .setParameter("bookId", paramDto.getBookId())
                .setParameter("bookIsbn", paramDto.getIsbn())
                .setParameter("bookName", Objects.isNull(paramDto.getName()) ? null : paramDto.getName().toLowerCase())
                .setParameter("authorId", paramDto.getAuthorId())
                .setParameter("genreCode", paramDto.getGenreCode())
                .getResultList();
    }

    @Override
    public Book save(@Valid Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public void deleteById(long bookId) {
        em.createQuery("delete from Book b where b.id = :bookId")
                .setParameter("bookId", bookId)
                .executeUpdate();
    }

    @Override
    public void delete(@Valid Book book) {
        em.remove(book);
    }
}