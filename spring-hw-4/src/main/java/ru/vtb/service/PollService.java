package ru.vtb.service;

import ru.vtb.model.Question;

import java.util.List;

public interface PollService {

    /**
     * Ask specified questions and return amount of correctly answered ones
     *
     * @param questions list of questions
     *
     * @return amount of correctly answered questions
     */
    int askQuestions(List<Question> questions);
}
