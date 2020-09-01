package ru.vtb.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class BookDto {

    private String id;
    @NotEmpty
    @Size(min = 1, max = 120)
    private String isbn;
    @NotEmpty
    @Size(min = 1, max = 120)
    private String name;

    private String authorId;
    private String authorFirstName;
    private String authorLastName;

    private String genreCode;
    private String genreName;

    public boolean isNew() {
        return id == null || id.isEmpty();
    }
}
