package ru.vtb.dto.page;

import ru.vtb.dto.BookDto;
import ru.vtb.dto.PageDto;

import java.util.List;

public class BookPageDto extends PageDto<BookDto> {

    public BookPageDto(List<BookDto> data,
                        int currentPage,
                        int totalPage,
                        boolean hasNext,
                        boolean hasPrevious) {
        super(data, currentPage, totalPage, hasNext, hasPrevious);
    }
}
