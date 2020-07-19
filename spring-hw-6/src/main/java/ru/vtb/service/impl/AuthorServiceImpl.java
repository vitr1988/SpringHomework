package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.dao.AuthorDao;
import ru.vtb.model.Author;
import ru.vtb.service.AuthorService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorDao authorDao;

    @Override
    public List<Author> findAll() {
        return authorDao.findAll();
    }

    @Override
    public Optional<Author> getById(long authorId) {
        return authorDao.getById(authorId);
    }

    @Override
    @Transactional
    public Author save(@Valid Author author) {
        return authorDao.save(author);
    }

    @Override
    @Transactional
    public void deleteById(long authorId) {
        authorDao.deleteById(authorId);
    }

    @Override
    @Transactional
    public void delete(@Valid Author author) {
        authorDao.delete(author);
    }
}
