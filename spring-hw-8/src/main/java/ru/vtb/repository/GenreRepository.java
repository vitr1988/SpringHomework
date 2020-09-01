package ru.vtb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.vtb.model.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {
}
