package ru.vtb.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.vtb.config.ApplicationProperties;
import ru.vtb.exception.FetchQuestionException;
import ru.vtb.model.Question;
import ru.vtb.service.PollService;
import ru.vtb.service.impl.PollReader;
import ru.vtb.util.LocalizationHelper;

import java.util.List;
import java.util.Objects;

@ShellComponent
public class UserInteraction {

    private final PollService pollService;
    private final List<Question> questions;
    private final LocalizationHelper localizationHelper;
    private final int minAmountQuestions;

    private String interviewee;
    private Integer amountOfCorrectAnsweredQuestions;

    public UserInteraction(PollService pollService, PollReader pollReader,
                           LocalizationHelper localizationHelper, ApplicationProperties properties)
            throws FetchQuestionException {
        this.pollService = pollService;
        this.questions = pollReader.getQuestions();
        this.localizationHelper = localizationHelper;
        this.minAmountQuestions = properties.getPoll().getMinQuestions();
    }

    @ShellMethod(value = "Welcome command", key = {"g", "greet", "greeting"})
    public String greeting(@ShellOption(defaultValue = "Guest") String userName) {
        this.interviewee = userName;
        this.amountOfCorrectAnsweredQuestions = null; // if change user, reset last results
        return localizationHelper.localize("welcome", new String[]{userName});
    }

    @ShellMethod(value = "Ask questions event command", key = {"q", "question"})
    @ShellMethodAvailability(value = "isAuthorized")
    public void askQuestions() {
        this.amountOfCorrectAnsweredQuestions = pollService.askQuestions(questions);
    }

    @ShellMethod(value = "Result of questionnaire event command", key = {"r", "result"})
    @ShellMethodAvailability(value = "polled")
    public String results() {
        return String.format(localizationHelper.localize("total",
                this.interviewee, this.amountOfCorrectAnsweredQuestions, this.minAmountQuestions));
    }

    private Availability isAuthorized() {
        return Objects.isNull(interviewee) ? Availability.unavailable(localizationHelper.localize("error.logonNotHappened")):
                Availability.available();
    }

    private Availability polled() {
        return Objects.isNull(amountOfCorrectAnsweredQuestions) ? Availability.unavailable(localizationHelper.localize("error.takePartInPoll")):
                Availability.available();
    }
}
