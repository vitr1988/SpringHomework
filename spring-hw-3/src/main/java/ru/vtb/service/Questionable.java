package ru.vtb.service;

import ru.vtb.model.Question;

import java.io.IOException;
import java.util.List;

public interface Questionable {

    List<Question> getQuestions() throws IOException;
}
