package ru.vtb.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.dto.AuthorDto;
import ru.vtb.mapper.AuthorMapper;
import ru.vtb.repository.AuthorRepository;
import ru.vtb.service.AuthorService;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(commandKey="findAllAuthors", fallbackMethod="fallbackAuthors")
    public List<AuthorDto> findAll() {
        return authorMapper.toDtos(authorRepository.findAll());
    }

    public List<AuthorDto> fallbackAuthors() {
        return Arrays.asList(AuthorDto.NO_AUTHOR);
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(commandProperties= {
        @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000")
    })
    public Optional<AuthorDto> getById(long authorId) {
        return authorMapper.toOptionalDto(authorRepository.findById(authorId));
    }

    @Override
    @Transactional
    public AuthorDto save(@Valid AuthorDto author) {
        return authorMapper.toDto(authorRepository.save(authorMapper.toEntity(author)));
    }

    @Override
    @Transactional
    public void deleteById(long authorId) {
        authorRepository.deleteById(authorId);
    }

    @Override
    @Transactional
    public void delete(@Valid AuthorDto author) {
        authorRepository.delete(authorMapper.toEntity(author));
    }
}
