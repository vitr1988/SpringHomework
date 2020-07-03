package ru.vtb.dao;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.dao.impl.GenreDaoJpa;
import ru.vtb.model.Genre;

import static java.util.function.Predicate.not;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с жанрами книг на основе JPA должен ")
@DataJpaTest
@Transactional
@Import(GenreDaoJpa.class)
public class GenreDaoTest {

    @Autowired
    private GenreDao genreDao;

    @DisplayName("уметь получать список всех жанров книг")
    @Test
    public void shouldReturnCorrectAllGenreList() {
        val genres = genreDao.findAll();
        assertThat(genres).isNotNull().hasSize(11)
                .allMatch(not(s -> s.getCode().isEmpty()))
                .allMatch(not(s -> s.getName().isEmpty()));
        genres.forEach(System.out::println);
    }

    @DisplayName("уметь загружать информацию о конкретном жанре книги по его идентификатору")
    @Test
    public void shouldFindExpectedGenreById(){
        String genreCode = "com";
        val genre = genreDao.getByCode(genreCode);
        assertThat(genre).isPresent();
        val actualAuthor = genre.get();
        assertThat(actualAuthor.getCode()).isEqualTo(genreCode);
        assertThat(actualAuthor.getName()).isEqualTo("Компьютерная литература");
    }

    @DisplayName("уметь создавать жанры книги, а потом загружать информацию о нем")
    @Test
    public void shouldSaveAndLoadCorrectGenre() {
        val expectedGenre = new Genre();
        expectedGenre.setCode("hor");
        expectedGenre.setName("Ужасы");
        val actualGenre = genreDao.save(expectedGenre);
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }


    @DisplayName("уметь обновлять наименование жанра книги в БД")
    @Test
    public void shouldUpdateGenre() {
        val genre = genreDao.getByCode("pov");
        assertThat(genre).isPresent();

        val expectedGenre = genre.get();
        val newName = "Повесть временных лет";
        expectedGenre.setName(newName);
        genreDao.save(expectedGenre);
        val actualGenre = genreDao.getByCode("pov");

        assertThat(actualGenre).isPresent().matches(a -> newName.equals(a.get().getName()));
    }

    @DisplayName("уметь удалять жанр книги")
    @Test
    public void shouldDeleteGenre() {
        val genreCountBefore = genreDao.findAll().size();
        val newGenre = new Genre();
        newGenre.setCode("neg");
        newGenre.setName("Несуществующий жанр");
        val genre = genreDao.save(newGenre);
        genreDao.delete(genre);
        val genreCountAfter = genreDao.findAll().size();

        assertThat(genreCountBefore).isEqualTo(genreCountAfter);
    }
}
