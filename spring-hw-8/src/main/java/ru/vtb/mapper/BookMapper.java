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
            @Mapping(target = "name", source = "book.name"),
            @Mapping(target = "authorId", source = "book.author.id"),
            @Mapping(target = "authorFirstName", source = "book.author.firstName"),
            @Mapping(target = "authorLastName", source = "book.author.lastName"),
            @Mapping(target = "genreCode", source = "book.genre.code"),
            @Mapping(target = "genreName", source = "book.genre.name")
    })
    BookDto toDto(Book book);

    @Mappings({
            @Mapping(target = "id", source = "book.id"),
            @Mapping(target = "isbn", source = "book.isbn"),
            @Mapping(target = "name", source = "book.name")
    })
    Book toEntity(BookDto book);
}
