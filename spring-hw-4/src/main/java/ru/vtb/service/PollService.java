package ru.vtb.service;

import ru.vtb.model.Question;

import java.util.List;

/**
 * Сервис опросника
 */
public interface PollService {

    /**
     * Задаем указанные вопросы пользователю
     *
     * @param questions список вопросов
     *
     * @return количество правильно отвеченных вопросов
     */
    int askQuestions(List<Question> questions);
}
