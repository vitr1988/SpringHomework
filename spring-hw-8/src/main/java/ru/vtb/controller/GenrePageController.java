package ru.vtb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vtb.dto.GenreDto;
import ru.vtb.service.GenreService;

import javax.validation.Valid;

@Validated
@Controller
@RequiredArgsConstructor
public class GenrePageController {

    private final GenreService genreService;

    @GetMapping("/genres")
    public String index() {
        return "genre/genres";
    }

    @GetMapping("/genre/add")
    public String newGenre() {
        return "genre/genre";
    }

    @GetMapping("/genre/edit")
    public String currentGenre(@RequestParam("code") String genreCode, Model model) {
        genreService.getByCode(genreCode).ifPresent(genre -> model.addAttribute("genre", genre));
        return "genre/genre";
    }

    @PostMapping("/genre/save")
    public String saveGenre(@Valid GenreDto genre) {
        genreService.save(genre);
        return "redirect:/genres";
    }
}
