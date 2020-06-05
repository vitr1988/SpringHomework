package ru.vtb;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.vtb.config.ApplicationConfiguration;
import ru.vtb.config.PollProperties;
import ru.vtb.model.Question;
import ru.vtb.service.Questionable;
import ru.vtb.util.IntHolder;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Runner {

    /**
     * Starting point for application
     *
     * @param args          input arguments
     * @throws Exception    raised exceptions
     */
    public static void main(String[] args) throws Exception {
        try (var ctx = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
             var scanner = new Scanner(System.in)) {
            Questionable reader = ctx.getBean(Questionable.class);
            PollProperties properties = ctx.getBean(PollProperties.class);
            final List<Question> questions = reader.getQuestions();

            System.out.println("Let's play! Please provide your name:");
            System.out.println(String.format("Nice, %s! Ask for the questions:", scanner.nextLine()));
            //TODO: what do you think about AtomicInteger here? Please tell me your opinion. Thanks in advance.
            final IntHolder successfullyAnsweredQuestions = new IntHolder(0);
            final Integer minAmountOfQuestions = properties.getMinAmountOfQuestions();
            new Random().ints(0, questions.size()).distinct()
                    .limit(minAmountOfQuestions).mapToObj(index -> questions.get(index))
                    .forEach(question -> {
                        int inputDigit;
                        System.out.println(question.formatted());
                        try {
                            inputDigit = scanner.nextInt();
                            if (question.isCorrectAnswer(inputDigit)) {
                                successfullyAnsweredQuestions.increment();
                            }
                        } catch (InputMismatchException ime) {
                            System.out.println("Be careful, that's allowed to type only the digits");
                            scanner.next(); // to make it possible for ask new input
                        }
                    });
            System.out.printf("Your score is %d of %d\n", successfullyAnsweredQuestions.getValue(), minAmountOfQuestions);
        }
    }
}
