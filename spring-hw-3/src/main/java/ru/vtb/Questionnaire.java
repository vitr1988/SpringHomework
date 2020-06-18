package ru.vtb;

import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.vtb.service.PollService;
import ru.vtb.service.Questionable;

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
            val questions = reader.getQuestions();
            pollService.askQuestions(questions);
        }
    }
}
