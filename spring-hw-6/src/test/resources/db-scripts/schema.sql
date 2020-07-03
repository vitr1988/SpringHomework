DROP TABLE IF EXISTS GENRE;
CREATE TABLE GENRE
(
    CODE VARCHAR(3)  NOT NULL PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL UNIQUE
);
DROP TABLE IF EXISTS AUTHOR;
CREATE TABLE AUTHOR
(
    ID IDENTITY NOT NULL PRIMARY KEY,
    FIRST_NAME VARCHAR(255) NOT NULL,
    LAST_NAME VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS BOOK;
CREATE TABLE BOOK
(
    ID IDENTITY NOT NULL PRIMARY KEY,
    ISBN       VARCHAR(20)  NOT NULL UNIQUE,
    NAME       VARCHAR(255) NOT NULL,
    AUTHOR_ID  BIGINT,
    GENRE_CODE VARCHAR(3),
    FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHOR (ID),
    FOREIGN KEY (GENRE_CODE) REFERENCES GENRE (CODE)
);

DROP TABLE IF EXISTS COMMENT;
CREATE TABLE COMMENT
(
    ID IDENTITY NOT NULL PRIMARY KEY,
    TEXT        VARCHAR(4000)  NOT NULL,
    BOOK_ID     BIGINT,
    FOREIGN KEY (BOOK_ID) REFERENCES BOOK (ID)
);

