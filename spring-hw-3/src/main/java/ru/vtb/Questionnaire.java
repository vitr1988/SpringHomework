package ru.vtb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.vtb.model.Question;
import ru.vtb.service.PollService;
import ru.vtb.service.Questionable;

import java.util.List;

@SpringBootApplication
public class Questionnaire {

    /**
     * Starting point for application
     *
     * @param args input arguments
     * @throws Exception raised exceptions
     */
    public static void main(String[] args) throws Exception {
        try (var context = SpringApplication.run(Questionnaire.class, args)) {
            Questionable reader = context.getBean(Questionable.class);
            PollService pollService = context.getBean(PollService.class);
            final List<Question> questions = reader.getQuestions();
            pollService.askQuestions(questions, System.in, System.out);
        }
    }
}
