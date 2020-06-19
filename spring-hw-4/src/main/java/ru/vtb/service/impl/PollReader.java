package ru.vtb.service.impl;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.vtb.config.ApplicationProperties;
import ru.vtb.model.Question;
import ru.vtb.service.Questionable;
import ru.vtb.util.LocalizationHelper;
import ru.vtb.util.SplitHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PollReader implements Questionable {

    /**
     * Location of csv file with questions
     */
    private final Resource file;
    private final LocalizationHelper localizationHelper;

    public PollReader(ApplicationProperties properties, LocalizationHelper localizationHelper) {
        this.file = properties.getPoll().getPath();
        this.localizationHelper = localizationHelper;
    }

    @Override
    public List<Question> getQuestions() throws IOException {
        try (var reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            return reader.lines()
                    .map(SplitHelper::getColumnValues)
                    .peek(columns -> {
                        if (!columns.isEmpty()) {
                            columns.set(0, localizationHelper.localize(columns.get(0)));
                        }
                    })
                    .map(Question::new)
                    .collect(Collectors.toList());
        }
    }
}
