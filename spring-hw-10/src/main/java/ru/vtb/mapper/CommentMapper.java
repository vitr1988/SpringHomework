package ru.vtb.mapper;

import org.mapstruct.Mapper;
import ru.vtb.dto.CommentDto;
import ru.vtb.model.Comment;

@Mapper
public interface CommentMapper extends AbstractMapper<Comment, CommentDto> {
}
