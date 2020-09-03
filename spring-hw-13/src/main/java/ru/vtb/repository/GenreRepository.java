package ru.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.vtb.model.Genre;

import static ru.vtb.security.Authorities.ROLE_ADMIN;

@PreAuthorize("isAuthenticated()")
public interface GenreRepository extends JpaRepository<Genre, String> {

    @Secured(ROLE_ADMIN)
    Genre save(Genre book);

    @Secured(ROLE_ADMIN)
    void delete(Genre genre);
}
