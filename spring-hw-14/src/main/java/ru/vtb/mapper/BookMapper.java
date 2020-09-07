package ru.vtb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BookMapper extends AbstractMapper<ru.vtb.model.sql.Book, ru.vtb.model.nosql.Book> {

    @Mapping(target = "comments", ignore = true)
    ru.vtb.model.nosql.Book toDocumentEntity(ru.vtb.model.sql.Book book);
}
