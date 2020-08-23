package ru.vtb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;
import ru.vtb.dto.BookDto;
import ru.vtb.mapper.BookMapper;
import ru.vtb.model.Book;
import ru.vtb.repository.BookRepository;
import ru.vtb.repository.GenreRepository;

import javax.validation.Valid;

@Validated
@Controller
@RequiredArgsConstructor
public class BookPageController {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    private final BookMapper bookMapper;

    @GetMapping("/books")
    public String allBooks() {
        return "book/books";
    }

    @GetMapping("/book/add")
    public String newBook(Model model) {
        model.addAttribute("genres", genreRepository.findAll());
        return "book/book";
    }

    @GetMapping("/book/edit")
    public String editBook(@RequestParam("id") String bookId, Model model) {
        model.addAttribute("book", bookRepository.findById(bookId));
        return "book/book";
    }

    @PostMapping("/book/save")
    public Mono<String> saveBook(@Valid BookDto bookDto) {
        return genreRepository.findById(bookDto.getGenreCode())
                .flatMap(genre -> {
                    Book book = bookMapper.toEntity(bookDto);
                    book.setGenre(genre);
                    return bookRepository.save(book);
                })
                .map(b -> "redirect:/books");
    }
}
