package ru.vtb.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vtb.model.sql.Genre;

public interface GenreJpaRepository extends JpaRepository<Genre, String> {
}
