package ru.vtb.service;

import ru.vtb.model.Question;

import java.io.IOException;
import java.util.List;

/**
 * Сервис работы с вопросами
 */
public interface Questionable {

    /**
     * Получение списка вопросов
     *
     * @return список полученных вопросов
     * @throws IOException исключение при чтении файлов
     */
    List<Question> getQuestions() throws IOException;
}
