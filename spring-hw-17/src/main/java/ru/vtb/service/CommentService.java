package ru.vtb.service;

import ru.vtb.dto.CommentDto;
import ru.vtb.model.Comment;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<CommentDto> findAll();
    Optional<CommentDto> getById(long commentId);
    CommentDto save(@Valid CommentDto comment);
    void deleteById(long commentId);
    void delete(@Valid Comment comment);
}
