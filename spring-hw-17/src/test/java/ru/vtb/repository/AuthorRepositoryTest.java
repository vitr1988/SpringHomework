package ru.vtb.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.model.Author;

import static java.util.function.Predicate.not;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с авторами книг на основе JPA должен ")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @DisplayName("уметь получать список всех авторов книг")
    @Test
    public void shouldReturnCorrectAllAuthorList() {
        val authors = authorRepository.findAll();
        assertThat(authors).isNotNull().hasSize(2)
                .allMatch(not(s -> s.getFirstName().isEmpty()))
                .allMatch(not(s -> s.getLastName().isEmpty()));
        authors.forEach(System.out::println);
    }

    @DisplayName("уметь загружать информацию о конкретном авторе книги по его идентификатору")
    @Test
    public void shouldFindExpectedAuthorById(){
        val author = authorRepository.getOne(1L);
        assertThat(author).isNotNull()
                .hasFieldOrPropertyWithValue("firstName", "Ильдар")
                .hasFieldOrPropertyWithValue("lastName", "Хабибулин");
    }

    @DisplayName("уметь создавать авторов книги, а потом загружать информацию о нем")
    @Test
    public void shouldSaveAndLoadCorrectAuthor() {
        val expectedAuthor = new Author();
        expectedAuthor.setFirstName("Виталий");
        expectedAuthor.setLastName("Иванов");
        val actualAuthor = authorRepository.save(expectedAuthor);
        assertThat(actualAuthor).isNotNull()
                .hasFieldOrPropertyWithValue("firstName", expectedAuthor.getFirstName())
                .hasFieldOrPropertyWithValue("lastName", expectedAuthor.getLastName());
    }


    @DisplayName("уметь обновлять имя автора книги в БД")
    @Test
    public void shouldUpdateAuthor() {
        val expectedAuthor = authorRepository.getOne(1L);
        assertThat(expectedAuthor).isNotNull();
        val newFirstName = "Самоучитель по Java";
        expectedAuthor.setFirstName(newFirstName);
        authorRepository.save(expectedAuthor);
        val actualAuthor = authorRepository.getOne(1L);

        assertThat(actualAuthor).isNotNull().matches(a -> newFirstName.equals(a.getFirstName()));
    }

    @DisplayName("уметь удалять автора книги")
    @Test
    public void shouldDeleteAuthor() {
        val authorCountBefore = authorRepository.findAll().size();
        val newAuthor = new Author();
        newAuthor.setFirstName("Виталий");
        newAuthor.setLastName("Иванов");
        val author = authorRepository.save(newAuthor);
        authorRepository.delete(author);
        val authorCountAfter = authorRepository.findAll().size();

        assertThat(authorCountBefore).isEqualTo(authorCountAfter);
    }
}
