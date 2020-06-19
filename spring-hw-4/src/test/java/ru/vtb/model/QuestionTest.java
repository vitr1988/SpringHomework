package ru.vtb.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

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
    @DisplayName("корректно обрабатывать исключения")
    public void shouldHandleException() {
        assertThrows(IllegalArgumentException.class, () -> new Question(Arrays.asList("1", "2")));
    }
}
