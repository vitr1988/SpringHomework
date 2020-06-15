package ru.vtb;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.vtb.config.ApplicationConfiguration;
import ru.vtb.model.Question;
import ru.vtb.service.PollService;
import ru.vtb.service.Questionable;

import java.util.List;

public class Runner {

    /**
     * Starting point for application
     *
     * @param args          input arguments
     * @throws Exception    raised exceptions
     */
    public static void main(String[] args) throws Exception {
        try (var ctx = new AnnotationConfigApplicationContext(ApplicationConfiguration.class)) {
            Questionable reader = ctx.getBean(Questionable.class);
            PollService pollService = ctx.getBean(PollService.class);
            final List<Question> questions = reader.getQuestions();
            pollService.askQuestions(questions, System.in, System.out);
        }
    }
}
