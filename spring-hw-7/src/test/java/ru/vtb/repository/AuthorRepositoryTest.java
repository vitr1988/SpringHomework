package ru.vtb.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.vtb.model.Author;
import ru.vtb.util.CollectionUtils;

import static java.util.function.Predicate.not;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с авторами книг на основе JPA должен ")
@DataJpaTest
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
        val author = authorRepository.findById(1L);
        assertThat(author).isPresent();
        val actualAuthor = author.get();
        assertThat(actualAuthor.getFirstName()).isEqualTo("Ильдар");
        assertThat(actualAuthor.getLastName()).isEqualTo("Хабибулин");
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
        val author = authorRepository.findById(1L);
        assertThat(author).isPresent();
        val expectedAuthor = author.get();
        val newFirstName = "Самоучитель по Java";
        expectedAuthor.setFirstName(newFirstName);
        authorRepository.save(expectedAuthor);
        val actualAuthor = authorRepository.findById(1L);

        assertThat(actualAuthor).isPresent().matches(a -> newFirstName.equals(a.get().getFirstName()));
    }

    @DisplayName("уметь удалять автора книги")
    @Test
    public void shouldDeleteAuthor() {
        val authorCountBefore = CollectionUtils.toList(authorRepository.findAll()).size();
        val newAuthor = new Author();
        newAuthor.setFirstName("Виталий");
        newAuthor.setLastName("Иванов");
        val author = authorRepository.save(newAuthor);
        authorRepository.delete(author);
        val authorCountAfter = CollectionUtils.toList(authorRepository.findAll()).size();

        assertThat(authorCountBefore).isEqualTo(authorCountAfter);
    }
}
