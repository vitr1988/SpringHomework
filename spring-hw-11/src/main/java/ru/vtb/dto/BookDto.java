package ru.vtb.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import ru.vtb.model.Genre;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class BookDto {

    private String id;
    @NotEmpty
    @Size(min = 1, max = 120)
    private String isbn;
    @NotEmpty
    @Size(min = 1, max = 120)
    private String name;
    @NotEmpty
    private String genreCode;
}
