package ru.vtb.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.vtb.model.nosql.Genre;

public interface GenreRepository extends MongoRepository<Genre, String> {
}
