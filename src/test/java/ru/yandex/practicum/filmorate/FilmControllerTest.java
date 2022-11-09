package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private FilmController filmController;
    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        filmController.findAllFilms().clear();
    }

    private Film getFilm() {
        return Film.builder()
                .name("Test")
                .description("Description")
                .releaseDate(LocalDate.of(1895, 12, 28))
                .duration(1)
                .build();
    }


    @Test
    public void givenFilmWithFailedName_whenCreate_thenStatus400() throws Exception {
        Film film = getFilm();
        film.setName("");
        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void givenFilmWithFailedDescription_whenCreate_thenStatus400() throws Exception {
        Film film = getFilm();
        film.setDescription("123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901");
        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void givenFilmWithFailedReleaseDate_whenCreate_thenStatus400() throws Exception {
        Film film = getFilm();
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void givenFilmWithFailedDuration_whenCreate_thenStatus400() throws Exception {
        Film film = getFilm();
        film.setDuration(0);
        mockMvc.perform(post("/films")
                        .content(objectMapper.writeValueAsString(film))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}
