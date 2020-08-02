package ru.vtb.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.dto.ResultDto;
import ru.vtb.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long bookId) {
        bookService.deleteById(bookId);
        return ResponseEntity.ok(new ResultDto());
    }
}
