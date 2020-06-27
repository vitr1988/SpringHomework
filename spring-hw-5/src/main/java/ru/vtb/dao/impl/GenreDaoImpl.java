package ru.vtb.dao.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import ru.vtb.dao.BookDao;
import ru.vtb.dao.GenreDao;
import ru.vtb.dao.dto.BookParamDto;
import ru.vtb.model.Genre;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Validated
@Repository
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcOperations jdbcOperations;
    private final RowMapper<Genre> genreRowMapper;

    public GenreDaoImpl(NamedParameterJdbcOperations jdbcOperations, BookDao bookDao) {
        this.jdbcOperations = jdbcOperations;
        this.genreRowMapper = (rs, row) -> {
            val genreCode = rs.getString("genre_code");
            val genre = new Genre(genreCode, rs.getString("genre_name"));
            genre.setBooks(bookDao.getByParams(new BookParamDto(genreCode)));
            return genre;
        };
    }

    @Override
    public List<Genre> findAll() {
        //language=SQL
        val sql = "SELECT g.code genre_code, g.name genre_name FROM db.GENRE g";
        return jdbcOperations.query(sql, genreRowMapper);
    }

    @Override
    public Optional<Genre> getByCode(@NotEmpty String genreCode) {
        //language=SQL
        val sql = "SELECT g.code genre_code, g.name genre_name FROM db.GENRE g " +
                "where g.code = :genreCode";
        try {
            return Optional.of(jdbcOperations.queryForObject(sql, Map.of("genreCode", genreCode), genreRowMapper));
        } catch (Exception e) {
            log.error("Problems during getting info about genre by code {}", genreCode, e);
            return Optional.empty();
        }
    }

    @Override
    public String create(@Valid Genre genre) {
        //language=SQL
        val sqlQuery = "insert into GENRE (code, name) values (:genreCode, :genreName)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        val genreCode = genre.getCode();
        namedParameters.addValue("genreCode", genreCode, Types.VARCHAR);
        namedParameters.addValue("genreName", genre.getName(), Types.VARCHAR);
        jdbcOperations.update(sqlQuery, namedParameters);
        return genreCode;
    }

    @Override
    public void update(@Valid Genre genre) {
        //language=SQL
        val sqlQuery = "update GENRE set name = :genreName " +
                "where code = :genreCode";
        jdbcOperations.update(sqlQuery, Map.of(
                "genreCode", genre.getCode(),
                "genreName", genre.getName()));
    }

    @Override
    public void deleteByCode(@NotEmpty String genreCode) {
        //language=SQL
        val sqlQuery = "delete from GENRE g where g.code = :genreCode";
        jdbcOperations.update(sqlQuery, Map.of("genreCode", genreCode));
    }
}
