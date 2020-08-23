package ru.vtb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.vtb.dto.BookDto;
import ru.vtb.model.Book;

@Mapper
public interface BookMapper extends AbstractMapper<Book, BookDto> {

    @Mappings({
            @Mapping(target = "id", source = "book.id"),
            @Mapping(target = "isbn", source = "book.isbn"),
            @Mapping(target = "name", source = "book.name")
    })
    BookDto toDto(Book book);
}
