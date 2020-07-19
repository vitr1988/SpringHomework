package ru.vtb.dao;

import ru.vtb.model.Genre;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

public interface GenreDao {
    List<Genre> findAll();
    Optional<Genre> getByCode(@NotEmpty String genreCode);
    Genre save(@Valid Genre genre);
    void deleteByCode(@NotEmpty String genreCode);
    void delete(@Valid Genre genre);
}
