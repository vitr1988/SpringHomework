package ru.vtb.dao;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.vtb.dao.impl.AuthorDaoJpa;
import ru.vtb.model.Author;

import static java.util.function.Predicate.not;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с авторами книг на основе JPA должен ")
@DataJpaTest
@Import(AuthorDaoJpa.class)
public class AuthorDaoTest {

    @Autowired
    private AuthorDao authorDao;

    @DisplayName("уметь получать список всех авторов книг")
    @Test
    public void shouldReturnCorrectAllAuthorList() {
        val authors = authorDao.findAll();
        assertThat(authors).isNotNull().hasSize(2)
                .allMatch(not(s -> s.getFirstName().isEmpty()))
                .allMatch(not(s -> s.getLastName().isEmpty()));
        authors.forEach(System.out::println);
    }

    @DisplayName("уметь загружать информацию о конкретном авторе книги по его идентификатору")
    @Test
    public void shouldFindExpectedAuthorById(){
        val author = authorDao.getById(1L);
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
        val actualAuthor = authorDao.save(expectedAuthor);
        assertThat(actualAuthor).isNotNull()
                .hasFieldOrPropertyWithValue("firstName", expectedAuthor.getFirstName())
                .hasFieldOrPropertyWithValue("lastName", expectedAuthor.getLastName());
    }


    @DisplayName("уметь обновлять имя автора книги в БД")
    @Test
    public void shouldUpdateAuthor() {
        val author = authorDao.getById(1L);
        assertThat(author).isPresent();
        val expectedAuthor = author.get();
        val newFirstName = "Самоучитель по Java";
        expectedAuthor.setFirstName(newFirstName);
        authorDao.save(expectedAuthor);
        val actualAuthor = authorDao.getById(1L);

        assertThat(actualAuthor).isPresent().matches(a -> newFirstName.equals(a.get().getFirstName()));
    }

    @DisplayName("уметь удалять автора книги")
    @Test
    public void shouldDeleteAuthor() {
        val authorCountBefore = authorDao.findAll().size();
        val newAuthor = new Author();
        newAuthor.setFirstName("Виталий");
        newAuthor.setLastName("Иванов");
        val author = authorDao.save(newAuthor);
        authorDao.delete(author);
        val authorCountAfter = authorDao.findAll().size();

        assertThat(authorCountBefore).isEqualTo(authorCountAfter);
    }
}
