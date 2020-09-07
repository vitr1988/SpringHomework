package ru.vtb.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vtb.model.sql.Author;

public interface AuthorJpaRepository extends JpaRepository<Author, Long> {
}
