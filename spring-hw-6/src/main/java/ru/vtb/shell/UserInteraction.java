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
import ru.vtb.dao.AuthorDao;
import ru.vtb.dao.BookDao;
import ru.vtb.dao.CommentDao;
import ru.vtb.dao.GenreDao;
import ru.vtb.dao.dto.BookParamDto;
import ru.vtb.model.Author;
import ru.vtb.model.Book;
import ru.vtb.model.Comment;
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

    private final BookDao bookDao;
    private final GenreDao genreDao;
    private final AuthorDao authorDao;
    private final CommentDao commentDao;

    private final LocalizationHelper localizationHelper;

    private String username;

    @ShellMethod(value = "Welcome command", key = {"w", "welcome"})
    public String welcome(@ShellOption(defaultValue = "Guest") String userName) {
        this.username = userName;
        return localizationHelper.localize("welcome", new String[]{userName});
    }

    @ShellMethod(value = "Create book", key = {"cb", "create book"})
    @ShellMethodAvailability(value = "isAuthorized")
    public void createBook(@ShellOption(value = {"-is", "--isbn"}) String isbn,
                           @ShellOption(value = {"-n", "--name"}) String name,
                           @ShellOption(value = {"-ai", "--authorId"}) Long authorId,
                           @ShellOption(value = {"-g", "--genre"}) String genreCode) throws DataAccessException {
        Book newBook = new Book(isbn, name);
        Optional.ofNullable(authorId).flatMap(authorDao::getById).ifPresent(newBook::setAuthor);
        Optional.ofNullable(genreCode).flatMap(genreDao::getByCode).ifPresent(newBook::setGenre);
        Book book = bookDao.save(newBook);
        log.info("Created book has id {}", book.getId());
    }

    @ShellMethod(value = "Find book", key = {"fb", "find book"})
    @ShellMethodAvailability(value = "isAuthorized")
    public void findBook(@ShellOption(value = {"-i", "--id"}, defaultValue = ShellOption.NULL) Long id,
                         @ShellOption(value = {"-is", "--isbn"}, defaultValue = ShellOption.NULL) String isbn,
                         @ShellOption(value = {"-n", "--name"}, defaultValue = ShellOption.NULL) String name,
                         @ShellOption(value = {"-ai", "--authorId"}, defaultValue = ShellOption.NULL) Long authorId,
                         @ShellOption(value = {"-g", "--genre"}, defaultValue = ShellOption.NULL) String genreCode) throws DataAccessException {
        val paramDto = new BookParamDto(id, isbn, name, authorId, genreCode);
        List<Book> books = bookDao.getByParams(paramDto);
        log.info("Fetch books {}", books);
    }

    @ShellMethod(value = "Update book", key = {"ub", "update book"})
    @ShellMethodAvailability(value = "isAuthorized")
    public void updateBook(@ShellOption(value = {"-i", "--id"}) Long id,
                           @ShellOption(value = {"-is", "--isbn"}, defaultValue = ShellOption.NULL) String isbn,
                           @ShellOption(value = {"-n", "--name"}, defaultValue = ShellOption.NULL) String name,
                           @ShellOption(value = {"-ai", "--authorId"}, defaultValue = ShellOption.NULL) Long authorId,
                           @ShellOption(value = {"-g", "--genre"}, defaultValue = ShellOption.NULL) String genreCode) throws DataAccessException {
        bookDao.getById(id).ifPresent(book -> {
            if (Objects.nonNull(isbn) && !Objects.equals(isbn, book.getIsbn())) {
                book.setIsbn(isbn);
            }
            if (Objects.nonNull(name) && !Objects.equals(name, book.getName())) {
                book.setName(name);
            }
            authorDao.getById(authorId).ifPresent(book::setAuthor);
            genreDao.getByCode(genreCode).ifPresent(book::setGenre);
            book = bookDao.save(book);
            log.info("Updated book has id {}", book.getId());
        });
    }

    @ShellMethod(value = "Delete book", key = {"db", "delete book"})
    @ShellMethodAvailability(value = "isAuthorized")
    public void deleteBook(@ShellOption(value = {"-i", "--id"}) Long id) throws DataAccessException {
        bookDao.deleteById(id);
        log.info("Deleted book's id was {}", id);
    }

    @ShellMethod(value = "Create genre", key = {"cg", "create genre"})
    @ShellMethodAvailability(value = "isAuthorized")
    public void createGenre(@ShellOption(value = {"-c", "--code"}) String code,
                            @ShellOption(value = {"-n", "--name"}) String name) throws DataAccessException {
        val genre = genreDao.save(new Genre(code, name));
        log.info("Created genre has code {}", genre.getCode());
    }

    @ShellMethod(value = "Find genre", key = {"fg", "find genre"})
    @ShellMethodAvailability(value = "isAuthorized")
    public void findGenre(@ShellOption(value = {"-c", "--code"}, defaultValue = ShellOption.NULL) String code,
                          @ShellOption(value = {"-n", "--name"}, defaultValue = ShellOption.NULL) String name) throws DataAccessException {
        List<Genre> genres;
        if (Objects.isNull(code)) {
            genres = genreDao.findAll();
        } else {
            Optional<Genre> genre = genreDao.getByCode(code);
            genres = genre.isEmpty() ? Collections.emptyList() : Collections.singletonList(genre.get());
        }
        log.info("Fetch genres {}", genres);
    }

    @ShellMethod(value = "Update genre", key = {"ug", "update genre"})
    @ShellMethodAvailability(value = "isAuthorized")
    public void updateGenre(@ShellOption(value = {"-c", "--code"}) String code,
                            @ShellOption(value = {"-n", "--name"}) String name) throws DataAccessException {
        genreDao.getByCode(code).ifPresent(genre -> {
            if (!Objects.isNull(name) && !Objects.equals(name, genre.getName())) {
                genre.setName(name);
                genre = genreDao.save(genre);
                log.info("Updated genre has code {}", genre.getCode());
            }
        });
    }

    @ShellMethod(value = "Delete genre", key = {"dg", "delete genre"})
    @ShellMethodAvailability(value = "isAuthorized")
    public void deleteGenre(@ShellOption(value = {"-c", "--code"}) String code) throws DataAccessException {
        genreDao.deleteByCode(code);
        log.info("Deleted genre's code was {}", code);
    }

    @ShellMethod(value = "Create author", key = {"ca", "create author"})
    @ShellMethodAvailability(value = "isAuthorized")
    public void createAuthor(@ShellOption(value = {"-f", "--first", "--firstName"}) String firstName,
                             @ShellOption(value = {"-l", "--last", "--lastName"}) String lastName) throws DataAccessException {
        val author = authorDao.save(new Author(firstName, lastName));
        log.info("Created author has id {}", author.getId());
    }

    @ShellMethod(value = "Find author", key = {"fa", "find author"})
    @ShellMethodAvailability(value = "isAuthorized")
    public void findAuthor(@ShellOption(value = {"-i", "--id"}, defaultValue = ShellOption.NULL) Long id) throws DataAccessException {
        List<Author> authors;
        if (id == null) {
            authors = authorDao.findAll();
        } else {
            Optional<Author> author = authorDao.getById(id);
            authors = author.map(Collections::singletonList).orElse(Collections.emptyList());
        }
        log.info("Fetch authors {}", authors);
    }

    @ShellMethod(value = "Update author", key = {"ua", "update author"})
    @ShellMethodAvailability(value = "isAuthorized")
    public void updateAuthor(@ShellOption(value = {"-i", "--id"}) Long id,
                             @ShellOption(value = {"-f", "--first", "--firstName"}, defaultValue = ShellOption.NULL) String firstName,
                             @ShellOption(value = {"-l", "--last", "--lastName"}, defaultValue = ShellOption.NULL) String lastName) throws DataAccessException {
        authorDao.getById(id).ifPresent(author -> {
            if (Objects.nonNull(firstName) && !Objects.equals(firstName, author.getFirstName())) {
                author.setFirstName(firstName);
            }
            if (Objects.nonNull(lastName) && !Objects.equals(lastName, author.getLastName())) {
                author.setLastName(lastName);
            }
            author = authorDao.save(author);
            log.info("Updated author has id {}", author.getId());
        });
    }

    @ShellMethod(value = "Delete author", key = {"da", "delete author"})
    @ShellMethodAvailability(value = "isAuthorized")
    public void deleteAuthor(@ShellOption(value = {"-i", "--id"}) Long id) throws DataAccessException {
        authorDao.deleteById(id);
        log.info("Deleted author's id was {}", id);
    }

    @ShellMethod(value = "Create comment", key = {"cc", "create comment"})
    @ShellMethodAvailability(value = "isAuthorized")
    public void createComment(@ShellOption(value = {"-t", "--text"}) String text,
                              @ShellOption(value = {"-b", "--book", "--bookId"}) long bookId) throws DataAccessException {
        var comment = new Comment(text);
        bookDao.getById(bookId).ifPresent(comment::setBook);
        comment = commentDao.save(comment);
        log.info("Created comment has id {}", comment.getId());
    }

    @ShellMethod(value = "Find comment", key = {"fc", "find comment"})
    @ShellMethodAvailability(value = "isAuthorized")
    public void findComment(@ShellOption(value = {"-i", "--id"}, defaultValue = ShellOption.NULL) Long id) throws DataAccessException {
        List<Comment> comments;
        if (id == null) {
            comments = commentDao.findAll();
        } else {
            Optional<Comment> author = commentDao.getById(id);
            comments = author.map(Collections::singletonList).orElse(Collections.emptyList());
        }
        log.info("Fetch comments {}", comments);
    }

    @ShellMethod(value = "Update comment", key = {"uc", "update comment"})
    @ShellMethodAvailability(value = "isAuthorized")
    public void updateComment(@ShellOption(value = {"-i", "--id"}) Long id,
                              @ShellOption(value = {"-t", "--text"}) String text) throws DataAccessException {
        commentDao.getById(id).ifPresent(comment -> {
            comment.setText(text);
            comment = commentDao.save(comment);
            log.info("Updated comment has id {}", comment.getId());
        });
    }

    @ShellMethod(value = "Delete comment", key = {"dc", "delete comment"})
    @ShellMethodAvailability(value = "isAuthorized")
    public void deleteComment(@ShellOption(value = {"-i", "--id"}) Long id) throws DataAccessException {
        commentDao.deleteById(id);
        log.info("Deleted book comment's id was {}", id);
    }

    private Availability isAuthorized() {
        return Objects.isNull(username) ? Availability.unavailable(localizationHelper.localize("error.logonNotHappened")) :
                Availability.available();
    }

}
