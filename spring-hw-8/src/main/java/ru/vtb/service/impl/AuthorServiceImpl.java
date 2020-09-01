package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.dto.AuthorDto;
import ru.vtb.dto.PageDto;
import ru.vtb.mapper.AuthorMapper;
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
    private final AuthorMapper authorMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> findAll() {
        return authorMapper.toDtos(authorRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public PageDto<AuthorDto> getPage(Pageable pageable) {
        Page<Author> currentPage = authorRepository.findAll(pageable);
        return new PageDto<>(authorMapper.toDtos(currentPage.getContent()),
                currentPage.getNumber(),
                currentPage.getTotalPages(),
                currentPage.hasNext(),
                currentPage.hasPrevious());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorDto> getById(String authorId) {
        return authorMapper.toOptionalDto(authorRepository.findById(authorId));
    }

    @Override
    @Transactional
    public AuthorDto save(@Valid AuthorDto author) {
        return authorMapper.toDto(authorRepository.save(authorMapper.toEntity(author)));
    }

    @Override
    @Transactional
    public void deleteById(String authorId) {
        authorRepository.deleteById(authorId);
    }

    @Override
    @Transactional
    public void delete(@Valid AuthorDto author) {
        authorRepository.delete(authorMapper.toEntity(author));
    }
}
