package ru.vtb.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

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
}
