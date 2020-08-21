package ru.vtb.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class GenreDto {
    @NotNull
    @Size(min = 1, max = 3)
    private String code;

    @NotNull
    @Size(min = 1, max = 120)
    private String name;
}
