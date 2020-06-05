package ru.vtb.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Getter
@Component
public class PollProperties {

    @Value("${poll.path}") // mandatory field
    private Resource pollPath;

    @Value("${poll.questions.min:2}") // optional field
    private Integer minAmountOfQuestions;
}
