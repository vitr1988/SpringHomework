package ru.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vtb.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
