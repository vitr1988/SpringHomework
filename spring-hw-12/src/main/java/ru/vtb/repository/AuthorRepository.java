package ru.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vtb.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
