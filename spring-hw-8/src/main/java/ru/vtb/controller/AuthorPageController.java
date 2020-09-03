package ru.vtb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vtb.dto.AuthorDto;
import ru.vtb.service.AuthorService;

import javax.validation.Valid;

@Validated
@Controller
@RequiredArgsConstructor
public class AuthorPageController {

    private final AuthorService authorService;

    @GetMapping("/authors")
    public String allAuthors() {
        return "author/authors";
    }

    @GetMapping("/author/add")
    public String newAuthor() {
        return "author/author";
    }

    @GetMapping("/author/edit")
    public String currentAuthor(@RequestParam("id") String authorId, Model model) {
        authorService.getById(authorId).ifPresent(author -> model.addAttribute("author", author));
        return "author/author";
    }

    @PostMapping("/author/save")
    public String saveAuthor(@Valid AuthorDto author) {
        authorService.save(author);
        return "redirect:/authors";
    }
}
