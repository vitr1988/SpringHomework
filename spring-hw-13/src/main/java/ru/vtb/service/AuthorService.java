package ru.vtb.service;

import ru.vtb.dto.AuthorDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * TODO: в дальнейшем сервисы будут работать не напрямую с Entity, а с проекциями или dto
 */
public interface AuthorService {
    List<AuthorDto> findAll();
    Optional<AuthorDto> getById(long authorId);
    AuthorDto save(@Valid AuthorDto author);
    void deleteById(long authorId);
    void delete(@Valid AuthorDto author);
}
