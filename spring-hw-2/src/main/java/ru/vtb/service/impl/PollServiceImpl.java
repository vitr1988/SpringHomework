package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vtb.config.PollProperties;
import ru.vtb.model.Question;
import ru.vtb.service.PollService;
import ru.vtb.util.IntHolder;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class PollServiceImpl implements PollService {

    private final PollProperties properties;

    @Override
    public void askQuestions(List<Question> questions, InputStream is, PrintStream ps) {
        try (var scanner = new Scanner(is)) {
            ps.println("Let's play! Please provide your name:");
            ps.println(String.format("Nice, %s! Ask for the questions:", scanner.nextLine()));
            //TODO: what do you think about AtomicInteger here? Please tell me your opinion. Thanks in advance.
            final IntHolder successfullyAnsweredQuestions = new IntHolder(0);
            final Integer minAmountOfQuestions = properties.getMinAmountOfQuestions();
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
                            ps.println("Be careful, that's allowed to type only the digits");
                            scanner.next(); // to make it possible for ask new input
                        }
                    });
            ps.printf("Your score is %d of %d\n", successfullyAnsweredQuestions.getValue(), minAmountOfQuestions);
        }
    }
}
