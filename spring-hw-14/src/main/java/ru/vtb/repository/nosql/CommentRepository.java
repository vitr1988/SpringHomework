package ru.vtb.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.vtb.model.nosql.Comment;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
