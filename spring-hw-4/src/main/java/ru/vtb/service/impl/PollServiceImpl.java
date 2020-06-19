package ru.vtb.service.impl;

import lombok.val;
import org.springframework.stereotype.Service;
import ru.vtb.config.ApplicationProperties;
import ru.vtb.model.Question;
import ru.vtb.service.IOService;
import ru.vtb.service.PollService;
import ru.vtb.util.IntHolder;
import ru.vtb.util.LocalizationHelper;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;

@Service
public class PollServiceImpl implements PollService {

    private final LocalizationHelper localizationHelper;
    private final Integer minAmountOfQuestions;
    private final IOService ioService;

    public PollServiceImpl(LocalizationHelper localizationHelper, ApplicationProperties properties, IOService ioService) {
        this.localizationHelper = localizationHelper;
        this.minAmountOfQuestions = properties.getPoll().getMinQuestions();
        this.ioService = ioService;
    }

    @Override
    public int askQuestions(List<Question> questions) {
        val successfullyAnsweredQuestions = new IntHolder(0);
        new Random().ints(0, questions.size()).distinct()
                .limit(minAmountOfQuestions).mapToObj(questions::get)
                .forEach(question -> {
                    int inputDigit;
                    ioService.println(question.formatted());
                    try {
                        inputDigit = ioService.nextInt();
                        if (question.isCorrectAnswer(inputDigit)) {
                            successfullyAnsweredQuestions.increment();
                        }
                    } catch (InputMismatchException ime) {
                        ioService.println(localizationHelper.localize("error"));
                        ioService.nextLine(); // to make it possible for ask new input
                    }
                });
        val amountOfSuccessfulAnsweredQuestions = successfullyAnsweredQuestions.getValue();
        ioService.println(localizationHelper.localize("total", new Integer[]{amountOfSuccessfulAnsweredQuestions, minAmountOfQuestions}));
        return amountOfSuccessfulAnsweredQuestions;
    }
}
