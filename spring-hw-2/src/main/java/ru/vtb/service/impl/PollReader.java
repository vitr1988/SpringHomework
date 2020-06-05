package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.vtb.config.PollProperties;
import ru.vtb.model.Question;
import ru.vtb.service.Questionable;
import ru.vtb.util.SplitHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PollReader implements Questionable {

    /**
     * Location of csv file with questions
     * Next time it would be referenced from application.properties or application.yaml
     */
    private final Resource file;

    @Autowired
    public PollReader(PollProperties properties) {
        this.file = properties.getPollPath();
    }

    @Override
    public List<Question> getQuestions() throws IOException {
        try (var reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            return reader.lines().map(SplitHelper::getColumnValues)
                    .map(Question::new)
                    .collect(Collectors.toList());
        }
    }
}
