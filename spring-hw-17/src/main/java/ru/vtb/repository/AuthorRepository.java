package ru.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.vtb.model.Author;

@PreAuthorize("isAuthenticated()")
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
