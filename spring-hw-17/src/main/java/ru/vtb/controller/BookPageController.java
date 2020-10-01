package ru.vtb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vtb.dto.BookDto;
import ru.vtb.service.AuthorService;
import ru.vtb.service.BookService;
import ru.vtb.service.GenreService;

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
    public String editBook(@RequestParam("id") long bookId, Model model) {
        BookDto currentBook = bookService.getById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Non existed book"));
        model.addAttribute("book", currentBook);
        return "book/book";
    }

    @PostMapping("/book/save")
    public String saveBook(BookDto book) {
        bookService.partialSave(book);
        return "redirect:/books";
    }
}
