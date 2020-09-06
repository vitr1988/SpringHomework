package ru.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.vtb.model.Comment;

@PreAuthorize("isAuthenticated()")
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
