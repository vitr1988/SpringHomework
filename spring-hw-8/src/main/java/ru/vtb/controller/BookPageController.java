package ru.vtb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vtb.dto.BookDto;
import ru.vtb.dto.GenreDto;
import ru.vtb.mapper.AuthorMapper;
import ru.vtb.mapper.BookMapper;
import ru.vtb.mapper.GenreMapper;
import ru.vtb.model.Book;
import ru.vtb.service.AuthorService;
import ru.vtb.service.BookService;
import ru.vtb.service.GenreService;

import javax.validation.Valid;

@Validated
@Controller
@RequiredArgsConstructor
public class BookPageController {

    private final BookService bookService;
    private final GenreService genreService;
    private final AuthorService authorService;

    @GetMapping("/books")
    public String allBooks() {
        return "book/books";
    }

    @GetMapping("/book/add")
    public String newBook(Model model) {
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("authors", authorService.findAll());
        return "book/book";
    }

    @GetMapping("/book/edit")
    public String editBook(@RequestParam("id") String bookId, Model model) {
        bookService.getById(bookId).ifPresent(book -> model.addAttribute("book", book));
        return "book/book";
    }

    @PostMapping("/book/save")
    public String saveBook(@Valid BookDto bookDto) {
        if (bookDto.isNew()) {
            bookService.save(bookDto);
        } else {
            bookService.partialSave(bookDto);
        }
        return "redirect:/books";
    }
}
