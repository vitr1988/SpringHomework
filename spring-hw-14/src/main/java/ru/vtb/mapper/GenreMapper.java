package ru.vtb.mapper;

import org.mapstruct.Mapper;

@Mapper
public interface GenreMapper extends AbstractMapper<ru.vtb.model.sql.Genre, ru.vtb.model.nosql.Genre> {

}
