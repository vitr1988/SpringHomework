package ru.vtb.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.vtb.service.BookService;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("REST-контроллер для работы с книгами должен ")
@WebMvcTest(BookController.class)
public class BookControllerTest {

    private static final String BOOKS_URI = "/api/books";

    @Autowired
    protected MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private UserDetailsService userDetailsService;

    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    @Test
    @DisplayName("уметь получать список всех книг")
    public void getBooks() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(BOOKS_URI)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertThat(status).isEqualTo(200);
    }
}
