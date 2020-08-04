package ru.vtb.service;

import org.springframework.data.domain.Pageable;
import ru.vtb.dto.GenreDto;
import ru.vtb.dto.page.GenrePageDto;
import ru.vtb.model.Genre;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<GenreDto> findAll();
    GenrePageDto getPage(Pageable pageable);
    Optional<GenreDto> getByCode(@NotEmpty String genreCode);
    GenreDto save(@Valid GenreDto genre);
    void deleteByCode(@NotEmpty String genreCode);
    void delete(@Valid Genre genre);
}
