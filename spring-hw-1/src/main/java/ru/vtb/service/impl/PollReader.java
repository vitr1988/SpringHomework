package ru.vtb.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.vtb.dto.QuestionDto;
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
    private final String fileLocation;

    public List<QuestionDto> getQuestions() throws IOException {
        try (var reader = new BufferedReader(
                new InputStreamReader(getClass().getResourceAsStream(fileLocation)))) {
            return reader.lines().map(SplitHelper::getColumnValues)
                    .map(QuestionDto::new)
                    .collect(Collectors.toList());
        }
    }
}
