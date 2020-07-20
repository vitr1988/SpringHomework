package ru.vtb.repository;

import org.springframework.data.repository.CrudRepository;
import ru.vtb.model.Genre;

public interface GenreRepository extends CrudRepository<Genre, String> {
}
