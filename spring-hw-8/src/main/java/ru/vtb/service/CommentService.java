package ru.vtb.service;

import ru.vtb.model.Comment;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<Comment> findAll();
    Optional<Comment> getById(String commentId);
    Comment save(@Valid Comment comment);
    void deleteById(String commentId);
    void delete(@Valid Comment comment);
}
