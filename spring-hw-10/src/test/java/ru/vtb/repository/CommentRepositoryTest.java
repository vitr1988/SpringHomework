package ru.vtb.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.vtb.model.Comment;

import static java.util.function.Predicate.not;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с комментариями к книгам на основе JPA должен ")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("уметь получать список всех комментариев к книге")
    @Test
    public void shouldReturnCorrectAllCommentList() {
        val comments = commentRepository.findAll();
        assertThat(comments).isNotNull().hasSize(1)
                .allMatch(not(s -> s.getText().isEmpty()));
        comments.forEach(System.out::println);
    }

    @DisplayName("уметь загружать информацию о конкретном комментарии к книге по его идентификатору")
    @Test
    public void shouldFindExpectedCommentById(){
        val expectedComment = commentRepository.getOne(1L);
        assertThat(expectedComment).isNotNull()
                .hasFieldOrPropertyWithValue("text", "Лучшая книга, которую я читал");
    }

    @DisplayName("уметь создавать комментарии к книге, а потом загружать информацию о нем")
    @Test
    public void shouldSaveAndLoadCorrectComment() {
        val book = bookRepository.findById(1L);
        val expectedComment = new Comment();
        expectedComment.setText("Очередной комментарий");
        book.ifPresent(expectedComment::setBook);
        val actгalComment = commentRepository.save(expectedComment);
        assertThat(actгalComment).isNotNull()
                .hasFieldOrPropertyWithValue("text", "Очередной комментарий");
    }


    @DisplayName("уметь обновлять текст комментария к книге в БД")
    @Test
    public void shouldUpdateCommentText() {
        val expectedComment = commentRepository.getOne(1L);
        assertThat(expectedComment).isNotNull();
        val newText = "Какой-то новый комментарий";
        expectedComment.setText(newText);
        commentRepository.save(expectedComment);
        val actualComment = commentRepository.getOne(1L);
        assertThat(actualComment).isNotNull()
                .hasFieldOrPropertyWithValue("text", newText);
    }

    @DisplayName("уметь удалять комментарий к книге")
    @Test
    public void shouldDeleteComment() {
        val commentCountBefore = commentRepository.findAll().size();
        val newComment = new Comment();
        newComment.setText("Комментарий от Виталия");
        bookRepository.findById(1L).ifPresent(newComment::setBook);
        val comment = commentRepository.save(newComment);
        commentRepository.delete(comment);
        val commentCountAfter = commentRepository.findAll().size();
        assertThat(commentCountBefore).isEqualTo(commentCountAfter);
    }
}
