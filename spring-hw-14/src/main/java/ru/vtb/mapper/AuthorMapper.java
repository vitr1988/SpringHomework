package ru.vtb.mapper;

import org.mapstruct.Mapper;

@Mapper
public interface AuthorMapper extends
        AbstractMapper<ru.vtb.model.sql.Author, ru.vtb.model.nosql.Author> {
}
