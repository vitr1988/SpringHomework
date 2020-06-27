package ru.vtb.dao;

import ru.vtb.model.Genre;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

public interface GenreDao {
    List<Genre> findAll();
    Optional<Genre> getByCode(@NotEmpty String genreCode);
    String create(@Valid Genre genre);
    void update(@Valid Genre genre);
    void deleteByCode(@NotEmpty String genreCode);
}
