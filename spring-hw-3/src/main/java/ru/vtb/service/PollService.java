package ru.vtb.service;

import ru.vtb.model.Question;

import java.util.List;

public interface PollService {
    void askQuestions(List<Question> questions);
}
