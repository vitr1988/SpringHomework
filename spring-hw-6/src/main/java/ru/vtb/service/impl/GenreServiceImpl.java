package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.dao.GenreDao;
import ru.vtb.model.Genre;
import ru.vtb.service.GenreService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreDao genreDao;

    @Override
    public List<Genre> findAll() {
        return genreDao.findAll();
    }

    @Override
    public Optional<Genre> getByCode(@NotEmpty String genreCode) {
        return genreDao.getByCode(genreCode);
    }

    @Override
    @Transactional
    public Genre save(@Valid Genre genre) {
        return genreDao.save(genre);
    }

    @Override
    @Transactional
    public void deleteByCode(@NotEmpty String genreCode) {
        genreDao.deleteByCode(genreCode);
    }

    @Override
    @Transactional
    public void delete(@Valid Genre genre) {
        genreDao.delete(genre);
    }
}
