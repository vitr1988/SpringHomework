package ru.vtb.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CommentMapper extends AbstractMapper<ru.vtb.model.sql.Comment, ru.vtb.model.nosql.Comment> {

    @Mapping(target = "book", ignore = true)
    ru.vtb.model.nosql.Comment toDocumentEntity(ru.vtb.model.sql.Comment comment);
}
