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
import ru.vtb.model.Book;

import java.util.Objects;

import static java.util.function.Predicate.not;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с книгами на основе JPA должен ")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @DisplayName("уметь получать список всех книг")
    @Test
    public void shouldReturnCorrectAllBookList() {
        val books = bookRepository.findAll();
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
        val actualBook = bookRepository.getOne(1L);
        assertThat(actualBook).isNotNull()
                .hasFieldOrPropertyWithValue("name", "Самоучитель Java 2");
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

        genreRepository.findById("com").ifPresent(newBook::setGenre);
        authorRepository.findById(2L).ifPresent(newBook::setAuthor);
        val actualBook = bookRepository.save(newBook);
        assertThat(actualBook).isNotNull()
                .hasFieldOrPropertyWithValue("name", newBook.getName())
                .hasFieldOrPropertyWithValue("isbn", newBook.getIsbn());
    }


    @DisplayName("уметь обновлять имя книги в БД")
    @Test
    public void shouldUpdateBookName() {
        val expectedBook = bookRepository.getOne(1L);
        assertThat(expectedBook).isNotNull();
        val newBookName = "Самоучитель по Java";
        expectedBook.setName(newBookName);
        bookRepository.save(expectedBook);
        val actualBook = bookRepository.getOne(1L);

        assertThat(actualBook).isNotNull().matches(b -> newBookName.equals(b.getName()));
    }

    @DisplayName("уметь удалять книгу")
    @Test
    public void shouldDeleteBook() {
        val bookCountBefore = bookRepository.findAll().size();
        val newBook = new Book();
        newBook.setName("Колобок");
        newBook.setIsbn("1234567890");
        authorRepository.findById(2L).ifPresent(newBook::setAuthor);
        genreRepository.findById("chi").ifPresent(newBook::setGenre);
        val book = bookRepository.save(newBook);
        bookRepository.delete(book);
        val bookCountAfter = bookRepository.findAll().size();

        assertThat(bookCountBefore).isEqualTo(bookCountAfter);
    }
}
