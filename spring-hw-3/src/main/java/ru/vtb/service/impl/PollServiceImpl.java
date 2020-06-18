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
import java.util.Scanner;

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
    public void askQuestions(List<Question> questions) {
        try (var scanner = new Scanner(ioService.getInputStream())) {
            ioService.println(localizationHelper.localize("intro"));
            ioService.println(localizationHelper.localize("welcome", new String[]{scanner.nextLine()}));
            //TODO: what do you think about AtomicInteger here? Please tell me your opinion. Thanks in advance.
            val successfullyAnsweredQuestions = new IntHolder(0);
            new Random().ints(0, questions.size()).distinct()
                    .limit(minAmountOfQuestions).mapToObj(questions::get)
                    .forEach(question -> {
                        int inputDigit;
                        ioService.println(question.formatted());
                        try {
                            inputDigit = scanner.nextInt();
                            if (question.isCorrectAnswer(inputDigit)) {
                                successfullyAnsweredQuestions.increment();
                            }
                        } catch (InputMismatchException ime) {
                            ioService.println(localizationHelper.localize("error"));
                            scanner.next(); // to make it possible for ask new input
                        }
                    });
            ioService.println(localizationHelper.localize("total", new Integer[]{successfullyAnsweredQuestions.getValue(), minAmountOfQuestions}));
        }
    }
}
