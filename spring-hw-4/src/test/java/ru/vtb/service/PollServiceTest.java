package ru.vtb.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.vtb.config.ApplicationProperties;
import ru.vtb.model.Question;
import ru.vtb.service.impl.PollServiceImpl;
import ru.vtb.util.LocalizationHelper;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doAnswer;

@SpringBootTest
@DisplayName("Опросник должен ")
public class PollServiceTest {

    private PollService pollService;

    @MockBean
    private IOService ioService;

    @Autowired
    private LocalizationHelper localizationHelper;

    @Autowired
    private ApplicationProperties applicationProperties;

    @BeforeEach
    public void init() {
        doAnswer(invocation -> null).when(ioService).println(anyString());
        this.pollService = new PollServiceImpl(localizationHelper, applicationProperties, ioService);
    }

    @Test
    @DisplayName("уметь спрашивать вопросы")
    public void shouldAskQuestions() {
        Question question = new Question(Arrays.asList("Test", "1", "2", "1"));
        pollService.askQuestions(Collections.singletonList(question));
    }
}
