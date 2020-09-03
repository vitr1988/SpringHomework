package ru.vtb.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.vtb.model.Genre;

import static java.util.function.Predicate.not;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с жанрами книг на основе Mongo JPA должен ")
@DataMongoTest
public class GenreRepositoryTest {

    @Autowired
    private GenreRepository repository;

    @Test
    @DisplayName("уметь сохранять жанр и получать информацию по коду")
    public void shouldSaveAndFindByCode() {
        Genre genre = repository.save(new Genre("Art", "Искусство"));
        assertThat(genre.getCode()).isNotEmpty();
        assertThat(repository.findById("Art")).isNotNull();
    }
}
