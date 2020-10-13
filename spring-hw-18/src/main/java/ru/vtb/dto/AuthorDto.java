package ru.vtb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

    public static final AuthorDto NO_AUTHOR = new AuthorDto(-1L, "N/A", "N/A");

    private long id;
    private String firstName;
    private String lastName;

    public String getFullName() {
        return String.join(" ", lastName, firstName);
    }
}
