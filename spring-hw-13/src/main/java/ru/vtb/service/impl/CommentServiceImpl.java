package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.dto.CommentDto;
import ru.vtb.mapper.CommentMapper;
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
    private final CommentMapper commentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> findAll() {
        return commentMapper.toDtos(commentRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommentDto> getById(long commentId) {
        return commentMapper.toOptionalDto(commentRepository.findById(commentId));
    }

    @Override
    @Transactional
    public CommentDto save(@Valid CommentDto comment) {
        return commentMapper.toDto(commentRepository.save(commentMapper.toEntity(comment)));
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
