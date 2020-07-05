package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.dao.CommentDao;
import ru.vtb.model.Comment;
import ru.vtb.service.CommentService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    @Override
    public List<Comment> findAll() {
        return commentDao.findAll();
    }

    @Override
    public Optional<Comment> getById(long commentId) {
        return commentDao.getById(commentId);
    }

    @Override
    @Transactional
    public Comment save(@Valid Comment comment) {
        return commentDao.save(comment);
    }

    @Override
    @Transactional
    public void deleteById(long commentId) {
        commentDao.deleteById(commentId);
    }

    @Override
    @Transactional
    public void delete(@Valid Comment comment) {
        commentDao.delete(comment);
    }
}
