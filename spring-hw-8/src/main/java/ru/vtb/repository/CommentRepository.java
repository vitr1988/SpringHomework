package ru.vtb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.vtb.model.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
