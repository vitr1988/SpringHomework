package ru.vtb.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.vtb.dao.GenreDao;
import ru.vtb.model.Genre;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Optional;

@Slf4j
@Validated
@Repository
@RequiredArgsConstructor
public class GenreDaoJpa implements GenreDao {

    private final EntityManager em;

    @Override
    public List<Genre> findAll() {
        return em.createQuery("select g from Genre g").getResultList();
    }

    @Override
    public Optional<Genre> getByCode(@NotEmpty String genreCode) {
        return Optional.ofNullable(em.find(Genre.class, genreCode));
    }

    @Override
    @Transactional
    public Genre save(@Valid Genre genre) {
        if (em.find(Genre.class, genre.getCode()) == null) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    @Transactional
    public void deleteByCode(@NotEmpty String genreCode) {
        em.createQuery("delete from Genre g where g.code = :code")
                .setParameter("code", genreCode).executeUpdate();
    }

    @Override
    @Transactional
    public void delete(@Valid Genre genre) {
        em.remove(genre);
    }
}
