package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.model.Author;
import ru.vtb.repository.AuthorRepository;
import ru.vtb.service.AuthorService;
import ru.vtb.util.CollectionUtils;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public List<Author> findAll() {
        return CollectionUtils.toList(authorRepository.findAll());
    }

    @Override
    public Optional<Author> getById(long authorId) {
        return authorRepository.findById(authorId);
    }

    @Override
    @Transactional
    public Author save(@Valid Author author) {
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public void deleteById(long authorId) {
        authorRepository.deleteById(authorId);
    }

    @Override
    @Transactional
    public void delete(@Valid Author author) {
        authorRepository.delete(author);
    }
}
