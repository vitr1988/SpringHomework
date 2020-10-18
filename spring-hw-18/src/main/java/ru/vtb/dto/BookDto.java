package ru.vtb.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class BookDto {

    public static final BookDto NO_BOOK = new BookDto(-1L);

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

    private BookDto(long id) {
        this.id = id;
    }
}
