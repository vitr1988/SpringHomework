package ru.vtb.repository.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vtb.model.sql.Book;

public interface BookJpaRepository extends JpaRepository<Book, Long> {

}