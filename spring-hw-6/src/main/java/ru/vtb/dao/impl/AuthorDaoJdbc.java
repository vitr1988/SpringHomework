package ru.vtb.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import ru.vtb.dao.AuthorDao;
import ru.vtb.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@Validated
@Repository
public class AuthorDaoJdbc implements AuthorDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Author> findAll() {
        return em.createQuery("select a from Author a").getResultList();
    }

    @Override
    public Optional<Author> getById(long authorId) {
        return Optional.ofNullable(em.find(Author.class, authorId));
    }

    @Override
    public Author save(@Valid Author author) {
        if (author.getId() == 0) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public void deleteById(long authorId) {
        em.createQuery("delete from Author a where a.id = :authorId")
                .setParameter("authorId", authorId)
                .executeUpdate();
    }

    @Override
    public void delete(@Valid Author author) {
        em.remove(author);
    }
}
