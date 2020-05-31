package ru.vtb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.vtb.model.Question;
import ru.vtb.service.Questionable;

@Slf4j
public class Runner {

    /**
     * Location to spring-context xml
     */
    private static final String CONTEXT_LOCATION = "/spring-context.xml";

    public static void main(String[] args) throws Exception {
        try (var ctx = new ClassPathXmlApplicationContext(CONTEXT_LOCATION)) {
            Questionable reader = ctx.getBean(Questionable.class);
            reader.getQuestions().stream().map(Question::formatted)
                    .forEach(log::info);
        }
    }
}
