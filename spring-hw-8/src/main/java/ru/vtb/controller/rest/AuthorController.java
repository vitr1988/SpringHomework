package ru.vtb.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vtb.dto.AuthorDto;
import ru.vtb.dto.GenreDto;
import ru.vtb.dto.PageDto;
import ru.vtb.service.AuthorService;
import ru.vtb.service.GenreService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public PageDto<AuthorDto> find(@PageableDefault(value = 5) Pageable pageable) {
        return authorService.getPage(pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String authorId) {
        authorService.deleteById(authorId);
        return ResponseEntity.noContent().build();
    }
}
