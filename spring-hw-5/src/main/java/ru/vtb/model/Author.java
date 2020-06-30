package ru.vtb.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class Author {
    private long id;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    private List<Book> books;

    public Author(long id) {
        this.id = id;
    }

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Author(Long id, String firstName, String lastName) {
        this(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", firstName='" + firstName + "'" +
                ", lastName='" + lastName + "'" +
                (Objects.isNull(books) ? "" : ", books=" + books) +
                "}";
    }
}
