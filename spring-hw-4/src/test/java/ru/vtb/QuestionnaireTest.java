package ru.vtb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vtb.service.impl.PollReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayName("Опросник должен ")
public class QuestionnaireTest {

    @Autowired
    private PollReader reader;

    @Test
    @DisplayName("уметь считывать вопросы из внешнего файла")
    public void shouldReadQuestionsFromFile() throws IOException {
        assertEquals(1, reader.getQuestions().size());
    }
}
