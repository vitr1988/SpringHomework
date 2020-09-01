package ru.vtb.service;

import org.springframework.data.domain.Pageable;
import ru.vtb.dto.AuthorDto;
import ru.vtb.dto.PageDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * TODO: в дальнейшем сервисы будут работать не напрямую с Entity, а с проекциями или dto
 */
public interface AuthorService {
    List<AuthorDto> findAll();
    PageDto<AuthorDto> getPage(Pageable pageable);
    Optional<AuthorDto> getById(String authorId);
    AuthorDto save(@Valid AuthorDto author);
    void deleteById(String authorId);
    void delete(@Valid AuthorDto author);
}
