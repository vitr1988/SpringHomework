package ru.vtb.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@NoArgsConstructor
public class Book {

    private Long id;
    @NotEmpty
    private String isbn;
    @NotEmpty
    private String name;
    @NotNull
    private Author author;
    @NotNull
    private Genre genre;

    public Book(String isbn, String name) {
        this.isbn = isbn;
        this.name = name;
    }

    public Book(String isbn, String name, Long authorId, String genreCode) {
        this(isbn, name);
        if (!Objects.isNull(authorId)) {
            this.author = new Author(authorId);
        }
        if (!Objects.isNull(genreCode)) {
            this.genre = new Genre(genreCode);
        }
    }

    public Long getAuthorId() {
        return author == null ? null : author.getId();
    }

    public String getGenreCode() {
        return genre == null ? null : genre.getCode();
    }
}
