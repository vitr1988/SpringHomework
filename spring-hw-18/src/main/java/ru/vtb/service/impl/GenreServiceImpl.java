package ru.vtb.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.dto.GenreDto;
import ru.vtb.dto.PageDto;
import ru.vtb.mapper.GenreMapper;
import ru.vtb.model.Genre;
import ru.vtb.repository.GenreRepository;
import ru.vtb.security.Authorities;
import ru.vtb.service.GenreService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    private final Authorities authorities;

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(commandKey="findAllGenres", fallbackMethod="fallbackGenres")
    public List<GenreDto> findAll() {
        return genreMapper.toDtos(genreRepository.findAll());
    }

    public List<GenreDto> fallbackGenres() {
        return Arrays.asList(GenreDto.NO_GENRE);
    }

    @Override
    public PageDto<GenreDto> getPage(Pageable pageable) {
        Page<Genre> currentPage = genreRepository.findAll(pageable);
        return new PageDto<>(genreMapper.toDtos(currentPage.getContent()),
                currentPage.getNumber(),
                currentPage.getTotalPages(),
                currentPage.hasNext(),
                currentPage.hasPrevious(),
                authorities.isAdmin());
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(commandProperties= {
        @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="2000")
    })
    public Optional<GenreDto> getByCode(@NotEmpty String genreCode) {
        return genreMapper.toOptionalDto(genreRepository.findById(genreCode));
    }

    @Override
    @Transactional
    public GenreDto save(@Valid GenreDto genre) {
        return genreMapper.toDto(genreRepository.save(genreMapper.toEntity(genre)));
    }

    @Override
    @Transactional
    public void deleteByCode(@NotEmpty String genreCode) {
        genreRepository.deleteById(genreCode);
    }

    @Override
    @Transactional
    public void delete(@Valid Genre genre) {
        genreRepository.delete(genre);
    }
}
