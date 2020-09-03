package ru.vtb.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.vtb.model.Book;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с книгами на основе Mongo JPA должен ")
@DataMongoTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Test
    @DisplayName("уметь сохранять книги и получать информацию по идентификатору")
    public void shouldSaveAndFindById() {
        Book book = repository.save(new Book("4564-4564", "Новая книга"));
        assertThat(book.getId()).isNotEmpty();
        assertThat(repository.findById(book.getId())).isNotNull();
    }
}
