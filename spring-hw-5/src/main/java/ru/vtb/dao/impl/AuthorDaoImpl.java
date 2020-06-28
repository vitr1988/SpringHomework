package ru.vtb.dao.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import ru.vtb.dao.AuthorDao;
import ru.vtb.dao.BookDao;
import ru.vtb.dao.dto.BookParamDto;
import ru.vtb.model.Author;

import javax.validation.Valid;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Validated
@Repository
public class AuthorDaoImpl implements AuthorDao {

    private final NamedParameterJdbcOperations jdbcOperations;
    private final RowMapper<Author> authorRowMapper;

    public AuthorDaoImpl(NamedParameterJdbcOperations jdbcOperations, BookDao bookDao) {
        this.jdbcOperations = jdbcOperations;
        this.authorRowMapper = (rs, row) -> {
            val authorId = rs.getLong("author_id");
            val author = new Author(authorId, rs.getString("first_name"), rs.getString("last_name"));
            author.setBooks(bookDao.getByParams(new BookParamDto(authorId)));
            return author;
        };
    }

    @Override
    public List<Author> findAll() {
        //language=SQL
        val sql = "SELECT a.id author_id, a.first_name first_name, a.last_name last_name FROM AUTHOR a";
        return jdbcOperations.query(sql, authorRowMapper);
    }

    @Override
    public Optional<Author> getById(long authorId) {
        //language=SQL
        val sql = "SELECT a.id author_id, a.first_name first_name, a.last_name last_name FROM AUTHOR a " +
                "where a.id = :authorId";
        try {
            return Optional.of(jdbcOperations.queryForObject(sql, Map.of("authorId", authorId), authorRowMapper));
        } catch (Exception e) {
            log.error("Problems during getting info about author by id {}", authorId, e);
            return Optional.empty();
        }
    }

    @Override
    public long create(@Valid Author author) {
        //language=SQL
        val sqlQuery = "insert into AUTHOR (id, first_name, last_name) values (:authorId, :firstName, :lastName)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("authorId", author.getId(), Types.NUMERIC);
        namedParameters.addValue("firstName", author.getFirstName(), Types.VARCHAR);
        namedParameters.addValue("lastName", author.getLastName(), Types.VARCHAR);
        jdbcOperations.update(sqlQuery, namedParameters, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public void update(@Valid Author author) {
        //language=SQL
        val sqlQuery = "update AUTHOR set first_name = :firstName, last_name = :lastName " +
                "where id = :authorId";
        jdbcOperations.update(sqlQuery, Map.of(
                "authorId", author.getId(),
                "firstName", author.getFirstName(),
                "lastName", author.getLastName()));
    }

    @Override
    public void deleteById(long authorId) {
        //language=SQL
        val sqlQuery = "delete from AUTHOR a where a.id = :authorId";
        jdbcOperations.update(sqlQuery, Map.of("authorId", authorId));
    }
}
