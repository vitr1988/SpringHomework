package ru.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vtb.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, String> {
}
