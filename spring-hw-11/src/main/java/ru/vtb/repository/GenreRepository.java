package ru.vtb.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.vtb.model.Genre;

public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {
}
