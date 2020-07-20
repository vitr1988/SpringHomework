package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.model.Comment;
import ru.vtb.repository.CommentRepository;
import ru.vtb.service.CommentService;
import ru.vtb.util.CollectionUtils;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public List<Comment> findAll() {
        return CollectionUtils.toList(commentRepository.findAll());
    }

    @Override
    public Optional<Comment> getById(long commentId) {
        return commentRepository.findById(commentId);
    }

    @Override
    @Transactional
    public Comment save(@Valid Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteById(long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public void delete(@Valid Comment comment) {
        commentRepository.delete(comment);
    }
}
