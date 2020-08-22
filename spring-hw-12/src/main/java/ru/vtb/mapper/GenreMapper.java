package ru.vtb.mapper;

import org.mapstruct.Mapper;
import ru.vtb.dto.GenreDto;
import ru.vtb.model.Genre;

@Mapper
public interface GenreMapper extends AbstractMapper<Genre, GenreDto>{

}
