package ru.vtb.dto.page;

import ru.vtb.dto.GenreDto;
import ru.vtb.dto.PageDto;

import java.util.List;

public class GenrePageDto extends PageDto<GenreDto> {

    public GenrePageDto(List<GenreDto> data,
                        int currentPage,
                        int totalPage,
                        boolean hasNext,
                        boolean hasPrevious) {
        super(data, currentPage, totalPage, hasNext, hasPrevious);
    }
}
