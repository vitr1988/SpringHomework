package ru.vtb.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vtb.dto.GenreDto;
import ru.vtb.dto.PageDto;
import ru.vtb.service.GenreService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public PageDto<GenreDto> find(@PageableDefault(value = 5) Pageable pageable) {
        return genreService.getPage(pageable);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> delete(@PathVariable("code") String genreCode) {
        genreService.deleteByCode(genreCode);
        return ResponseEntity.noContent().build();
    }
}
