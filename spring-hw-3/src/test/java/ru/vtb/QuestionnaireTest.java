package ru.vtb;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import ru.vtb.configuration.AppConfig;
import ru.vtb.service.impl.PollReader;

import java.io.IOException;

public class QuestionnaireTest {

    private static AbstractApplicationContext context;

    @BeforeClass
    public static void init() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @Test
    public void testPollReaderFromContext() throws IOException {
        PollReader reader = context.getBean(PollReader.class);
        Assert.assertEquals(1, reader.getQuestions().size());
    }

    @AfterClass
    public static void shutdown() {
        context.close();
    }
}
