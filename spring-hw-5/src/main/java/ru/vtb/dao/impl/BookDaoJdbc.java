package ru.vtb.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import ru.vtb.dao.BookDao;
import ru.vtb.dao.dto.BookParamDto;
import ru.vtb.model.Author;
import ru.vtb.model.Book;
import ru.vtb.model.Genre;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Types;
import java.util.*;

@Slf4j
@Validated
@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {

    private final NamedParameterJdbcOperations jdbcOperations;

    private final RowMapper<Book> bookRowMapper = (rs, rowNum) -> {
        val book = new Book(rs.getString("isbn"), rs.getString("name"));
        book.setId(rs.getLong("book_id"));
        val genreCode = rs.getString("genre_code");
        val genre = new Genre(genreCode, rs.getString("genre_name"));
        book.setGenre(genre);
        val authorId = rs.getLong("author_id");
        val author = new Author(authorId, rs.getString("first_name"), rs.getString("last_name"));
        book.setAuthor(author);
        return book;
    };

    @Override
    public List<Book> findAll() {
        //language=SQL
        val sql = "SELECT b.id book_id, b.isbn, b.name, b.genre_code, g.name genre_name, a.id author_id, a.first_name, a.last_name FROM BOOK b " +
                "JOIN GENRE g ON g.code = b.genre_code " +
                "JOIN AUTHOR a ON a.id = b.author_id ";
        return jdbcOperations.query(sql, bookRowMapper);
    }

    @Override
    public Optional<Book> getById(long bookId) {
        //language=SQL
        val sql = "SELECT b.id book_id, b.isbn, b.name, b.genre_code, g.name genre_name, a.id author_id, a.first_name, a.last_name FROM BOOK b " +
                "JOIN GENRE g ON g.code = b.genre_code " +
                "JOIN AUTHOR a ON a.id = b.author_id " +
                "where b.id = :bookId";
        try {
            return Optional.of(jdbcOperations.queryForObject(sql, Map.of("bookId", bookId), bookRowMapper));
        } catch (Exception e) {
            log.error("Problems during getting info about book by id {}", bookId, e);
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getByParams(@NotNull BookParamDto paramDto) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("bookId", paramDto.getBookId());
        paramMap.put("bookIsbn", paramDto.getIsbn());
        paramMap.put("bookName", Objects.isNull(paramDto.getName()) ? null : paramDto.getName().toLowerCase());
        paramMap.put("authorId", paramDto.getAuthorId());
        paramMap.put("genreCode", paramDto.getGenreCode());
        //language=SQL
        val sql = "SELECT b.id book_id, b.isbn, b.name, b.genre_code, g.name genre_name, a.id author_id, a.first_name, a.last_name FROM BOOK b " +
                "JOIN GENRE g ON g.code = b.genre_code " +
                "JOIN AUTHOR a ON a.id = b.author_id " +
                "where (:bookId is null or b.id = :bookId) and " +
                "(:bookIsbn is null or b.isbn = :bookIsbn) and " +
                "(:bookName is null or lower(b.name) like CONCAT('%', :bookName ,'%')) and " +
                "(:authorId is null or a.id = :authorId) and " +
                "(:genreCode is null or g.code = :genreCode) ";
        return jdbcOperations.query(sql, paramMap, bookRowMapper);
    }

    @Override
    public long create(@Valid Book book) {
        //language=SQL
        val sqlQuery = "insert into BOOK (isbn, name, author_id, genre_code) values (:isbn, :name, :authorId, :genreCode)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("isbn", book.getIsbn(), Types.VARCHAR);
        namedParameters.addValue("name", book.getName(), Types.VARCHAR);
        namedParameters.addValue("authorId", book.getAuthorId(), Types.NUMERIC);
        namedParameters.addValue("genreCode", book.getGenreCode(), Types.VARCHAR);
        jdbcOperations.update(sqlQuery, namedParameters, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(@Valid Book book) {
        //language=SQL
        val sqlQuery = "update BOOK set isbn = :isbn, name = :name, author_id = :authorId, genre_code = :genreCode " +
                "where id = :id";
        jdbcOperations.update(sqlQuery, Map.of("id", book.getId(),
                "isbn", book.getIsbn(),
                "name", book.getName(),
                "authorId", book.getAuthorId(),
                "genreCode", book.getGenreCode()));
    }

    @Override
    public void deleteById(long bookId) {
        //language=SQL
        val sqlQuery = "delete from BOOK b where b.id = :bookId";
        jdbcOperations.update(sqlQuery, Map.of("bookId", bookId));
    }
}