package ru.vtb.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Список вопросов должен ")
public class QuestionTest {

    @Test
    @DisplayName("содержать сам вопрос, список вариантов ответов и непосредственно правильный ответ")
    public void shouldContainMainParts() {
        Question questionDto = new Question(Arrays.asList("1", "2", "3", "1"));
        assertEquals("1", questionDto.getQuestion());
        assertArrayEquals(new String[] {"2", "3"}, questionDto.getAnswers().toArray());

    }

    @Test
    @DisplayName("корректно обрабатывать исключения для вопросов из двух колонок")
    public void shouldHandleExceptionWith2Columns() {
        assertThrows(IllegalArgumentException.class, () -> new Question(Arrays.asList("1", "2")));
    }

    @Test
    @DisplayName("корректно обрабатывать исключения для вопросов с пустым списком колонок")
    public void shouldHandleExceptionWithEmptyCollection() {
        assertThrows(IllegalArgumentException.class, () -> new Question(Collections.emptyList()));
    }
}
