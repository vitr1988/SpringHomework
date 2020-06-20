package ru.vtb.service;

import ru.vtb.exception.FetchQuestionException;
import ru.vtb.model.Question;

import java.io.IOException;
import java.util.List;

public interface Questionable {

    /**
     * Receiving list of questions
     *
     * @return list of questions
     * @throws IOException исключение при чтении файлов
     */
    List<Question> getQuestions() throws FetchQuestionException;
}
