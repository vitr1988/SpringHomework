package ru.vtb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.vtb.model.Book;
import ru.vtb.repository.dto.BookParamDto;

import javax.validation.constraints.NotNull;
import java.util.List;

import static ru.vtb.security.Authorities.ROLE_ADMIN;

@PreAuthorize("isAuthenticated()")
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from Book b " +
            "join fetch b.author a " +
            "join fetch b.genre g " +
            "left join fetch b.comments " +
            "where (:#{#paramDto.bookId} is null or b.id = :#{#paramDto.bookId}) and " +
            "(:#{#paramDto.isbn} is null or b.isbn = :#{#paramDto.isbn}) and " +
            "(:#{#paramDto.name} is null or lower(b.name) like CONCAT('%', :#{#paramDto.name} ,'%')) and " +
            "(:#{#paramDto.authorId} is null or a.id = :#{#paramDto.authorId}) and " +
            "(:#{#paramDto.genreCode} is null or g.code = :#{#paramDto.genreCode})")
    List<Book> getByParams(@NotNull BookParamDto paramDto);

    Page<Book> findAll(Pageable page);

    @Secured(ROLE_ADMIN)
    Book save(Book book);

    @PreAuthorize("hasAuthority(T(ru.vtb.security.Authorities).ROLE_ADMIN) && hasPermission(#book, 'DELETE')")
    void delete(@Param("book") Book book);
}