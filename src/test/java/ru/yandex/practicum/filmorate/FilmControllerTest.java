package ru.yandex.practicum.filmorate;


import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.PropertyBatchUpdateException;
import org.springframework.beans.factory.parsing.Problem;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;

public class FilmControllerTest {
    private FilmController uc;

    private Film getFilm() {
        return Film.builder()
                .name("фильм")
                .description("Фильмов много — и с каждым годом становится всё больше.")
                .releaseDate(LocalDate.of(1921, 1, 1))
                .duration(90)
                .like(new HashSet<>())
                .build();
    }

    @BeforeEach
    public void beforeEach() {

        FilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
        UserStorage inMemoryUserStorage = new InMemoryUserStorage();
        FilmService filmService = new FilmService(inMemoryFilmStorage, inMemoryUserStorage);


        uc = new FilmController(inMemoryFilmStorage, filmService);
        Film film = getFilm();
        uc.addFilm(film);
    }


    @Test
    public void createFilmWithInvalidRelisDate() {

        Film updateFilm = getFilm();
        updateFilm.setReleaseDate(LocalDate.of(1721, 1, 1));
        RuntimeException exception;

        exception = assertThrows(ValidationException.class, () -> uc.inMemoryFilmStorage.checkValidationFilm(updateFilm));
        assertEquals(exception.getMessage(), exception.getMessage(), "Дата релиза фильм не должна быть ранее 28 декабря 1895 года .");
    }


    @Test
    public void createFilmWithWrongId() {

        Film updateFilm = getFilm();
        updateFilm.setId(1);
        RuntimeException exception;
        Film film = getFilm();

        exception = assertThrows(ValidationException.class, () -> uc.inMemoryFilmStorage.checkValidationFilm(updateFilm));
        assertEquals(exception.getMessage(), exception.getMessage(), "Фильм - " + film.getName() + " c id - " + film.getId() + " уже существует");
    }


    @Test
    public void createFilmWithNegativeId() {


        Film updateFilm = getFilm();
        updateFilm.setId(-1);
        RuntimeException exception;

        exception = assertThrows(ValidationException.class, () -> uc.inMemoryFilmStorage.checkValidationFilm(updateFilm));
        assertEquals(exception.getMessage(), exception.getMessage(), "Id Фильма не может быть отрицательным ");
    }

}