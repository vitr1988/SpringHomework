package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.model.Author;
import ru.vtb.repository.AuthorRepository;
import ru.vtb.service.AuthorService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
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
