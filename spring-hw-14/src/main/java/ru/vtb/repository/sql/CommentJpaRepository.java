package ru.vtb.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vtb.model.sql.Comment;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
}
