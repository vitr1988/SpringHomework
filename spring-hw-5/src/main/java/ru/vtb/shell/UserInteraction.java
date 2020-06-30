package ru.vtb.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.dao.DataAccessException;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.vtb.dao.ActionType;
import ru.vtb.dao.AuthorDao;
import ru.vtb.dao.BookDao;
import ru.vtb.dao.GenreDao;
import ru.vtb.dao.dto.BookParamDto;
import ru.vtb.model.Author;
import ru.vtb.model.Book;
import ru.vtb.model.Genre;
import ru.vtb.util.LocalizationHelper;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@ShellComponent
@RequiredArgsConstructor
public class UserInteraction {

    private final LocalizationHelper localizationHelper;
    private final BookDao bookDao;
    private final GenreDao genreDao;
    private final AuthorDao authorDao;

    private String username;

    @ShellMethod(value = "Welcome command", key = {"w", "welcome"})
    public String welcome(@ShellOption(defaultValue = "Guest") String userName) {
        this.username = userName;
        return localizationHelper.localize("welcome", new String[]{userName});
    }

    @ShellMethod(value = "Work with book", key = {"b", "book"})
    @ShellMethodAvailability(value = "isAuthorized")
    public void workWithBook(@ShellOption(value = {"-a", "--action"}, defaultValue = ActionType.Constants.RETRIEVE) ActionType action,
                             @ShellOption(value = {"-i", "--id"}, defaultValue = ShellOption.NULL) Long id,
                             @ShellOption(value = {"-is", "--isbn"}, defaultValue = ShellOption.NULL) String isbn,
                             @ShellOption(value = {"-n", "--name"}, defaultValue = ShellOption.NULL) String name,
                             @ShellOption(value = {"-ai", "--authorId"}, defaultValue = ShellOption.NULL) Long authorId,
                             @ShellOption(value = {"-g", "--genre"}, defaultValue = ShellOption.NULL) String genreCode) throws DataAccessException {
        switch (action) {
            case CREATE: {
                val book = new Book(isbn, name, authorId, genreCode);
                long bookId = bookDao.create(book);
                log.info("Created book has id {}", bookId);
                break;
            }
            case RETRIEVE: {
                val paramDto = new BookParamDto(id, isbn, name, authorId, genreCode);
                List<Book> books = bookDao.getByParams(paramDto);
                log.info("Fetch books {}", books);
                break;
            }
            case UPDATE: {
                if (Objects.isNull(id)) {
                    throw new IllegalArgumentException("Id should be mandatory");
                }
                bookDao.getById(id).ifPresent(book -> {
                    if (!Objects.isNull(isbn) && !Objects.equals(isbn, book.getIsbn())) {
                        book.setIsbn(isbn);
                    }
                    if (!Objects.isNull(name) && !Objects.equals(name, book.getName())) {
                        book.setName(name);
                    }
                    if (!Objects.isNull(authorId)) {
                        authorDao.getById(authorId).ifPresent(book::setAuthor);
                    }
                    if (!Objects.isNull(genreCode)) {
                        genreDao.getByCode(genreCode).ifPresent(book::setGenre);
                    }
                    bookDao.update(book);
                    log.info("Updated book has id {}", id);
                });
                break;
            }
            case DELETE: {
                if (Objects.isNull(id)) {
                    throw new IllegalArgumentException("Id should be mandatory");
                }
                bookDao.deleteById(id);
                log.info("Deleted book's id was {}", id);
                break;
            }
        }
    }

    @ShellMethod(value = "Work with genre", key = {"g", "genre"})
    @ShellMethodAvailability(value = "isAuthorized")
    public void workWithGenre(@ShellOption(value = {"-a", "--action"}, defaultValue = ActionType.Constants.RETRIEVE) ActionType action,
                              @ShellOption(value = {"-c", "--code"}, defaultValue = ShellOption.NULL) String code,
                              @ShellOption(value = {"-n", "--name"}, defaultValue = ShellOption.NULL) String name) throws DataAccessException {
        switch (action) {
            case CREATE: {
                val genre = new Genre(code, name);
                String genreCode = genreDao.create(genre);
                log.info("Created genre has code {}", genreCode);
                break;
            }
            case RETRIEVE: {
                List<Genre> genres;
                if (Objects.isNull(code)) {
                    genres = genreDao.findAll();
                } else {
                    Optional<Genre> genre = genreDao.getByCode(code);
                    genres = genre.isEmpty() ? Collections.emptyList() : Collections.singletonList(genre.get());
                }
                log.info("Fetch genres {}", genres);
                break;
            }
            case UPDATE: {
                if (Objects.isNull(code)) {
                    throw new IllegalArgumentException("Code should be mandatory");
                }
                genreDao.getByCode(code).ifPresent(genre -> {
                    if (!Objects.isNull(name) && !Objects.equals(name, genre.getName())) {
                        genre.setName(name);
                        genreDao.update(genre);
                        log.info("Updated genre has code {}", code);
                    }
                });
                break;
            }
            case DELETE: {
                if (Objects.isNull(code)) {
                    throw new IllegalArgumentException("Code should be mandatory");
                }
                genreDao.deleteByCode(code);
                log.info("Deleted genre's code was {}", code);
                break;
            }
        }
    }

    @ShellMethod(value = "Work with author", key = {"a", "author"})
    @ShellMethodAvailability(value = "isAuthorized")
    public void workWithAuthor(@ShellOption(value = {"-a", "--action"}, defaultValue = ActionType.Constants.RETRIEVE) ActionType action,
                               @ShellOption(value = {"-i", "--id"}, defaultValue = ShellOption.NULL) Long id,
                               @ShellOption(value = {"-f", "--first", "--firstName"}, defaultValue = ShellOption.NULL) String firstName,
                               @ShellOption(value = {"-l", "--last", "--lastName"}, defaultValue = ShellOption.NULL) String lastName) throws DataAccessException {
        switch (action) {
            case CREATE: {
                val author = new Author(firstName, lastName);
                long authorId = authorDao.create(author);
                log.info("Created author has id {}", authorId);
                break;
            }
            case RETRIEVE: {
                List<Author> authors;
                if (Objects.isNull(id)) {
                    authors = authorDao.findAll();
                }
                else {
                    Optional<Author> author = authorDao.getById(id);
                    authors = author.isEmpty() ? Collections.emptyList() : Collections.singletonList(author.get());
                }
                log.info("Fetch authors {}", authors);
                break;
            }
            case UPDATE: {
                if (Objects.isNull(id)) {
                    throw new IllegalArgumentException("Id should be mandatory");
                }
                authorDao.getById(id).ifPresent(author -> {
                    if (!Objects.isNull(firstName) && !Objects.equals(firstName, author.getFirstName())) {
                        author.setFirstName(firstName);
                    }
                    if (!Objects.isNull(lastName) && !Objects.equals(lastName, author.getLastName())) {
                        author.setLastName(lastName);
                    }
                    authorDao.update(author);
                    log.info("Updated author has id {}", id);
                });
                break;
            }
            case DELETE: {
                if (Objects.isNull(id)) {
                    throw new IllegalArgumentException("Id should be mandatory");
                }
                authorDao.deleteById(id);
                log.info("Deleted author's id was {}", id);
                break;
            }
        }
    }

    private Availability isAuthorized() {
        return Objects.isNull(username) ? Availability.unavailable(localizationHelper.localize("error.logonNotHappened")) :
                Availability.available();
    }

}
