package ru.vtb.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.vtb.model.Comment;

public interface CommentRepository extends ReactiveMongoRepository<Comment, String> {
}
