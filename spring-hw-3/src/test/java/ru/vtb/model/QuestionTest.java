package ru.vtb.model;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class QuestionTest {

    @Test
    public void testQuestion() {
        Question questionDto = new Question(Arrays.asList("1", "2", "3", "1"));
        assertEquals("1", questionDto.getQuestion());
        assertArrayEquals(new String[] {"2", "3"}, questionDto.getAnswers().toArray());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuestionWithException() {
        new Question(Arrays.asList("1", "2"));
    }
}
