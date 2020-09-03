package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.model.Comment;
import ru.vtb.repository.CommentRepository;
import ru.vtb.service.CommentService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> getById(String commentId) {
        return commentRepository.findById(commentId);
    }

    @Override
    @Transactional
    public Comment save(@Valid Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteById(String commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public void delete(@Valid Comment comment) {
        commentRepository.delete(comment);
    }
}
