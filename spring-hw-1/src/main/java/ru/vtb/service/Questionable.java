package ru.vtb.service;

import ru.vtb.dto.QuestionDto;

import java.io.IOException;
import java.util.List;

public interface Questionable {

    List<QuestionDto> getQuestions() throws IOException;
}
