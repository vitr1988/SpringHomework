package ru.vtb.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class Genre {
    private String code;
    @NotEmpty
    private String name;
    private List<Book> books;

    public Genre(String code) {
        this.code = code;
    }

    public Genre(String code, String name) {
        this(code);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "code='" + code + "'" +
                ", name='" + name + "'" +
                (Objects.isNull(books) ? "" : ", books=" + books) +
                "}";
    }
}
