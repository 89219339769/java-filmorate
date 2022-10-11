package ru.yandex.practicum.filmorate;


import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;

public class FilmControllerTest {



    private  FilmController uc;

    private Film getFilm() {
        return Film.builder()
                .id(1)
                .description("Фильмов много — и с каждым годом становится всё больше.")
                .name("фильм")
                .releaseDate(LocalDate.of(1955, 1, 1))
                .duration(90)
                .build();
    }

    @BeforeEach
    public void beforeEach() {
        FilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
        FilmService filmService = new  FilmService(inMemoryUserStorage);
        uc = new  FilmController(inMemoryFilmStorage,filmService);
        Film film = getFilm();
        uc.createFilm(film);
   }


    @Test
    public void createFilmWithInvalidRelisDate() {

        Film updateFilm = getFilm();
        updateFilm.setReleaseDate(LocalDate.of(1526, 1, 1));

        //  uc.createFilm(updateFilm);
        RuntimeException exception;

        exception = assertThrows(ValidationException.class, () -> uc.inMemoryFilmStorage.validateDateOfReliseFilm(updateFilm));
        assertEquals(exception.getMessage(), exception.getMessage(), "Дата релиза фильм не должна быть ранее 28 декабря 1895 года .");
    }


    @Test
    public void createFilmWithWrongId() {

        Film updateFilm = getFilm();
        updateFilm.setId(1);
        RuntimeException exception;
        Film film = getFilm();

        exception = assertThrows(ValidationException.class, () -> uc.inMemoryFilmStorage.validateFilmCreate(updateFilm));
        assertEquals(exception.getMessage(), exception.getMessage(), "Фильм - " + film.getName() + " c id - " + film.getId() + " уже существует");
    }


    @Test
    public void createFilmWithNegativeId() {


        Film updateFilm = getFilm();
        updateFilm.setId(-1);
        RuntimeException exception;

        exception = assertThrows(ValidationException.class, () -> uc.inMemoryFilmStorage.validateFilmId(updateFilm));
        assertEquals(exception.getMessage(), exception.getMessage(), "Id Фильма не может быть отрицательным ");
    }

}