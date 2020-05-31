package ru.vtb;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import ru.vtb.model.Question;
import ru.vtb.service.impl.PollReader;
import ru.vtb.util.SplitHelper;

import java.io.IOException;
import java.util.Arrays;

public class MainTest {

    private static AbstractApplicationContext context;

    @BeforeClass
    public static void init() {
        context = new ClassPathXmlApplicationContext("spring-test-context.xml");
    }

    @Test
    public void testPollReader() throws IOException {
        PollReader reader = new PollReader(new ClassPathResource("/testQuestions.csv"));
        Assert.assertEquals(1, reader.getQuestions().size());
    }

    @Test
    public void testPollReaderFromContext() throws IOException {
        PollReader reader = context.getBean(PollReader.class);
        Assert.assertEquals(1, reader.getQuestions().size());
    }

    @Test
    public void testSplitHelper() {
        Assert.assertTrue(SplitHelper.getColumnValues("").isEmpty());
        Assert.assertArrayEquals(new String[]{"Test", "Test2"}, SplitHelper.getColumnValues("Test;Test2").toArray());
    }

    @Test
    public void testQuestion() {
        Question questionDto = new Question(Arrays.asList("1", "2", "3", "1"));
        Assert.assertEquals("1", questionDto.getQuestion());
        Assert.assertArrayEquals(new String[] {"2", "3"}, questionDto.getAnswers().toArray());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuestionWithException() {
        new Question(Arrays.asList("1", "2"));
    }

    @AfterClass
    public static void shutdown() {
        context.close();
    }
}
