package ru.vtb.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vtb.dto.BookDto;
import ru.vtb.dto.PageDto;
import ru.vtb.service.BookService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public PageDto<BookDto> find(@PageableDefault(value = 5) Pageable pageable) {
        return bookService.getPage(pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long bookId) {
        bookService.deleteById(bookId);
        return ResponseEntity.noContent().build();
    }
}
