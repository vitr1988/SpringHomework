package ru.vtb.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.vtb.model.Author;
import ru.vtb.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с авторами книг на основе Mongo JPA должен ")
@DataMongoTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    @Test
    @DisplayName("уметь сохранять авторов и получать информацию по идентификатору")
    public void shouldSaveAndFindById() {
        Author author = repository.save(new Author("Иванов", "Виталий"));
        assertThat(author.getId()).isNotEmpty();
        assertThat(repository.findById(author.getId())).isNotNull();
    }
}
