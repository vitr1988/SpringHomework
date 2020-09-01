package ru.vtb.dto;

import lombok.Data;

@Data
public class AuthorDto {

    private String id;
    private String firstName;
    private String lastName;

    public String getFullName() {
        return String.join(" ", lastName, firstName);
    }
}
