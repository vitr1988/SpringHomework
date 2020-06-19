package ru.vtb.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.vtb.model.Question;
import ru.vtb.service.PollService;
import ru.vtb.service.impl.PollReader;
import ru.vtb.util.LocalizationHelper;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@ShellComponent
public class UserInteraction {

    private final PollService pollService;
    private final LocalizationHelper localizationHelper;
    private final List<Question> questions;

    private String interviewee;
    private Integer amountOfCorrectAnsweredQuestions;

    public UserInteraction(PollService pollService, PollReader pollReader, LocalizationHelper localizationHelper) throws IOException{
        this.pollService = pollService;
        this.questions = pollReader.getQuestions();
        this.localizationHelper = localizationHelper;
    }

    @ShellMethod(value = "Welcome command", key = {"e", "g", "enter", "greet"})
    public String greeting(@ShellOption(defaultValue = "Guest") String userName) {
        this.interviewee = userName;
        return localizationHelper.localize("welcome", new String[]{userName});
    }

    @ShellMethod(value = "Ask questions event command", key = {"q", "questions"})
    @ShellMethodAvailability(value = "isAuthorized")
    public void askQuestions() {
        this.amountOfCorrectAnsweredQuestions = pollService.askQuestions(questions);
    }

    @ShellMethod(value = "Result of questionnaire event command", key = {"r", "results"})
    @ShellMethodAvailability(value = "polled")
    public String results() {
        return String.format(localizationHelper.localize("total",
                this.interviewee, this.amountOfCorrectAnsweredQuestions, this.questions.size()));
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
