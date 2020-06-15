package ru.vtb.service;

import ru.vtb.model.Question;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

public interface PollService {
    void askQuestions(List<Question> questions, InputStream is, PrintStream ps);
}
