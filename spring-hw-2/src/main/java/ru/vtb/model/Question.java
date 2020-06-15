package ru.vtb.model;

import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

public class Question {

    private static final String ANSWER_TEMPLATE = "\n%d. \"%s\"";

    @Getter
    private final String question;
    @Getter
    private final List<String> answers;
    private final Integer correctAnswerIndex;

    public Question(List<String> rowColumns) {
        if (CollectionUtils.isEmpty(rowColumns) || rowColumns.size() < 3) {
            throw new IllegalArgumentException("Incorrect count of columns");
        }
        rowColumns = Collections.unmodifiableList(rowColumns);
        // question is in the first column
        this.question = rowColumns.get(0);
        // next ones keep answers
        final int lastIndex = rowColumns.size() - 1;

        this.answers = rowColumns.subList(1, lastIndex);
        // last one keep index of correct answers (starting from 1, not 0)
        this.correctAnswerIndex = Integer.decode(rowColumns.get(lastIndex));

        if (this.correctAnswerIndex <= 0 || this.correctAnswerIndex > this.answers.size()) {
            throw new IllegalArgumentException("Incorrect index of answer");
        }
    }

    public String formatted() {
        StringBuilder formattedText = new StringBuilder("\n").append(this.question);
        this.answers.stream().map(this::formattedAnswer).forEach(formattedText::append);
        return formattedText.toString();
    }

    private String formattedAnswer(String answer) {
        return String.format(ANSWER_TEMPLATE, answers.indexOf(answer) + 1, answer);
    }

    public boolean isCorrectAnswer(int variant) {
        return variant == correctAnswerIndex;
    }
}
