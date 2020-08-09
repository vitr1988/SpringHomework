package ru.vtb.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.vtb.service.GenreService;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("REST-контроллер для работы с жанрами книг должен ")
@WebMvcTest(BookController.class)
public class GenreControllerTest {

    private static final String GENRES_URI = "/api/genres";

    @Autowired
    protected MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GenreService genreService;

    @Test
    @DisplayName("уметь получать список всех жанров")
    public void getGenres() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(GENRES_URI)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertThat(status).isEqualTo(200);
    }
}
