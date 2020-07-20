package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.model.Genre;
import ru.vtb.repository.GenreRepository;
import ru.vtb.service.GenreService;
import ru.vtb.util.CollectionUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> findAll() {
        return CollectionUtils.toList(genreRepository.findAll());
    }

    @Override
    public Optional<Genre> getByCode(@NotEmpty String genreCode) {
        return genreRepository.findById(genreCode);
    }

    @Override
    @Transactional
    public Genre save(@Valid Genre genre) {
        return genreRepository.save(genre);
    }

    @Override
    @Transactional
    public void deleteByCode(@NotEmpty String genreCode) {
        genreRepository.deleteById(genreCode);
    }

    @Override
    @Transactional
    public void delete(@Valid Genre genre) {
        genreRepository.delete(genre);
    }
}
