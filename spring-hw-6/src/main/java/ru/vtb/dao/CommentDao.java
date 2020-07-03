package ru.vtb.dao;

import ru.vtb.model.Comment;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface CommentDao {
    List<Comment> findAll();
    Optional<Comment> getById(long commentId);
    Comment save(@Valid Comment comment);
    void deleteById(long commentId);
    void delete(@Valid Comment comment);
}
