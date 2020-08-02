package ru.vtb.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class BookDto {
    private long id;
    @NotEmpty
    @Size(min = 1, max = 120)
    private String isbn;
    @NotEmpty
    @Size(min = 1, max = 120)
    private String name;
    private long authorId;
    private String authorFirstName;
    private String authorLastName;
    private String genreCode;
    private String genreName;
    private List<CommentDto> comments;

    public String getIdStr() {
        return String.format("book_%d", id);
    }
}
