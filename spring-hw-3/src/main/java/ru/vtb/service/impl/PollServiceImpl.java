package ru.vtb.service.impl;

import org.springframework.stereotype.Service;
import ru.vtb.config.ApplicationProperties;
import ru.vtb.model.Question;
import ru.vtb.service.PollService;
import ru.vtb.util.IntHolder;
import ru.vtb.util.LocalizationHelper;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Service
public class PollServiceImpl implements PollService {

    private final LocalizationHelper localizationHelper;
    private final Integer minAmountOfQuestions;

    public PollServiceImpl(LocalizationHelper localizationHelper, ApplicationProperties properties) {
        this.localizationHelper = localizationHelper;
        this.minAmountOfQuestions = properties.getPoll().getMinQuestions();
    }


    @Override
    public void askQuestions(List<Question> questions, InputStream is, PrintStream ps) {
        try (var scanner = new Scanner(is)) {
            ps.println(localizationHelper.localize("intro"));
            ps.println(localizationHelper.localize("welcome", new String[]{scanner.nextLine()}));
            //TODO: what do you think about AtomicInteger here? Please tell me your opinion. Thanks in advance.
            final IntHolder successfullyAnsweredQuestions = new IntHolder(0);
            new Random().ints(0, questions.size()).distinct()
                    .limit(minAmountOfQuestions).mapToObj(questions::get)
                    .forEach(question -> {
                        int inputDigit;
                        ps.println(question.formatted());
                        try {
                            inputDigit = scanner.nextInt();
                            if (question.isCorrectAnswer(inputDigit)) {
                                successfullyAnsweredQuestions.increment();
                            }
                        } catch (InputMismatchException ime) {
                            ps.println(localizationHelper.localize("error"));
                            scanner.next(); // to make it possible for ask new input
                        }
                    });
            ps.println(localizationHelper.localize("total", new Integer[]{successfullyAnsweredQuestions.getValue(), minAmountOfQuestions}));
        }
    }
}
