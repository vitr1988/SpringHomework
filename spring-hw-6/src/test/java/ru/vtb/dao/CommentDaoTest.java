package ru.vtb.dao;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.dao.impl.BookDaoJpa;
import ru.vtb.dao.impl.CommentDaoJpa;
import ru.vtb.model.Comment;

import static java.util.function.Predicate.not;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DAO для работы с комментариями к книгам на основе JPA должен ")
@DataJpaTest
@Transactional
@Import({CommentDaoJpa.class, BookDaoJpa.class})
public class CommentDaoTest {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private BookDao bookDao;

    @DisplayName("уметь получать список всех комментариев к книге")
    @Test
    public void shouldReturnCorrectAllCommentList() {
        val comments = commentDao.findAll();
        assertThat(comments).isNotNull().hasSize(1)
                .allMatch(not(s -> s.getText().isEmpty()));
        comments.forEach(System.out::println);
    }

    @DisplayName("уметь загружать информацию о конкретном комментарии к книге по его идентификатору")
    @Test
    public void shouldFindExpectedCommentById(){
        val comment = commentDao.getById(1);
        assertThat(comment).isPresent();
        val actualComment = comment.get();
        assertThat(actualComment.getText()).isEqualTo("Лучшая книга, которую я читал");
    }

    @DisplayName("уметь создавать комментарии к книге, а потом загружать информацию о нем")
    @Test
    public void shouldSaveAndLoadCorrectComment() {
        val book = bookDao.getById(1L);
        val expectedComment = new Comment();
        expectedComment.setText("Очередной комментарий");
        book.ifPresent(expectedComment::setBook);
        val actгalComment = commentDao.save(expectedComment);
        assertThat(actгalComment).isNotNull()
                .hasFieldOrPropertyWithValue("text", "Очередной комментарий");
    }


    @DisplayName("уметь обновлять текст комментария к книге в БД")
    @Test
    public void shouldUpdateCommentText() {
        val comment = commentDao.getById(1L);
        assertThat(comment).isPresent();
        val expectedComment = comment.get();
        val newText = "Какой-то новый комментарий";
        expectedComment.setText(newText);
        commentDao.save(expectedComment);
        val actualComment = commentDao.getById(1L);

        assertThat(actualComment).isPresent().matches(a -> newText.equals(a.get().getText()));
    }

    @DisplayName("уметь удалять комментарий к книге")
    @Test
    public void shouldDeleteComment() {
        val commentCountBefore = commentDao.findAll().size();
        val newComment = new Comment();
        newComment.setText("Комментарий от Виталия");
        bookDao.getById(1L).ifPresent(newComment::setBook);
        val comment = commentDao.save(newComment);
        commentDao.delete(comment);
        val commentCountAfter = commentDao.findAll().size();
        assertThat(commentCountBefore).isEqualTo(commentCountAfter);
    }
}
