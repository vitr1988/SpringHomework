package ru.vtb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.vtb.service.impl.PollReader;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QuestionnaireTest {

    @Autowired
    private PollReader reader;

    @Test
    public void testPollReaderFromContext() throws IOException {
        assertEquals(1, reader.getQuestions().size());
    }
}
