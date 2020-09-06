package ru.vtb.mapper;

import org.mapstruct.Mapper;
import ru.vtb.dto.AuthorDto;
import ru.vtb.model.Author;

@Mapper
public interface AuthorMapper extends AbstractMapper<Author, AuthorDto> {
}
