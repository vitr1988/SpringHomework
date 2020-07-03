package ru.vtb.dao;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.dao.impl.AuthorDaoJpa;
import ru.vtb.dao.impl.BookDaoJpa;
import ru.vtb.dao.impl.GenreDaoJpa;
import ru.vtb.model.Book;

import java.util.Objects;

import static java.util.function.Predicate.not;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с книгами на основе JPA должен ")
@DataJpaTest
@Transactional
@Import({BookDaoJpa.class, AuthorDaoJpa.class, GenreDaoJpa.class})
public class BookDaoTest {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private GenreDao genreDao;

    @DisplayName("уметь получать список всех книг")
    @Test
    public void shouldReturnCorrectAllBookList() {
        val books = bookDao.findAll();
        assertThat(books).isNotNull().hasSize(1)
                .allMatch(not(s -> s.getName().isEmpty()))
                .allMatch(not(s -> s.getIsbn().isEmpty()))
                .allMatch(not(s -> Objects.isNull(s.getAuthor())))
                .allMatch(not(s -> Objects.isNull(s.getGenre())));
        books.forEach(System.out::println);
    }

    @DisplayName("уметь загружать информацию о конкретной книге по ее идентификатору")
    @Test
    public void shouldFindExpectedBookById(){
        val book = bookDao.getById(1);
        assertThat(book).isPresent();
        val actualBook = book.get();
        assertThat(actualBook.getName()).isEqualTo("Самоучитель Java 2");
        assertThat(actualBook.getAuthor()).isNotNull()
                .hasFieldOrPropertyWithValue("firstName", "Ильдар")
                .hasFieldOrPropertyWithValue("lastName", "Хабибулин");
        assertThat(actualBook.getGenre()).isNotNull()
                .hasFieldOrPropertyWithValue("code", "com");
    }

    @DisplayName("уметь создавать книги, а потом загружать информацию о ней")
    @Test
    public void shouldSaveAndLoadCorrectBook() {
        val newBook = new Book();
        newBook.setName("Spring in Action");
        newBook.setIsbn("9781617294945");

        genreDao.getByCode("com").ifPresent(newBook::setGenre);
        authorDao.getById(2L).ifPresent(newBook::setAuthor);
        val actualBook = bookDao.save(newBook);
        assertThat(actualBook).isNotNull()
                .hasFieldOrPropertyWithValue("name", newBook.getName())
                .hasFieldOrPropertyWithValue("isbn", newBook.getIsbn());
    }


    @DisplayName("уметь обновлять имя книги в БД")
    @Test
    public void shouldUpdateBookName() {
        val book = bookDao.getById(1);
        assertThat(book).isPresent();

        val expectedBook = book.get();
        val newBookName = "Самоучитель по Java";
        expectedBook.setName(newBookName);
        bookDao.save(expectedBook);
        val actualBook = bookDao.getById(1);

        assertThat(actualBook).isPresent().matches(b -> newBookName.equals(b.get().getName()));
    }

    @DisplayName("уметь удалять книгу")
    @Test
    public void shouldDeleteBook() {
        val bookCountBefore = bookDao.findAll().size();
        val newBook = new Book();
        newBook.setName("Колобок");
        newBook.setIsbn("1234567890");
        authorDao.getById(2L).ifPresent(newBook::setAuthor);
        genreDao.getByCode("chi").ifPresent(newBook::setGenre);
        val book = bookDao.save(newBook);
        bookDao.delete(book);
        val bookCountAfter = bookDao.findAll().size();

        assertThat(bookCountBefore).isEqualTo(bookCountAfter);
    }
}
