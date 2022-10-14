package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;

public class FilmControllerTest {
    private FilmController fc;

    private Film getFilm() {
        return Film.builder()
                .id(1)
                .description("Фильмов много — и с каждым годом становится всё больше.")
                .name("фильм")
                .releaseDate(LocalDate.of(1921, 1, 1))
                .duration(90)
                .build();

    }

    @BeforeEach
    public void beforeEach() {
        FilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
        UserStorage inMemoryUserStorage = new InMemoryUserStorage();
        FilmService filmService = new FilmService(inMemoryFilmStorage,inMemoryUserStorage);

        fc = new FilmController(inMemoryFilmStorage,filmService);
        Film film = getFilm();
        fc.addFilm(film);
    }

    @Test
    public void createFilmWithInvalidRelisDate() {

        Film updateFilm = getFilm();
        updateFilm.setReleaseDate(LocalDate.of(1721, 1, 1));
        RuntimeException exception;

        exception = assertThrows(ValidationException.class, () -> fc.inMemoryFilmStorage.checkValidationFilm(updateFilm));
        assertEquals(exception.getMessage(), exception.getMessage(), "дата релиза раньше 28 декабря 1895 года");
    }

    @Test
    public void createFilmWithInvalidDuration() {

        Film updateFilm = getFilm();
        updateFilm.setDuration(-90);
        RuntimeException exception;

        exception = assertThrows(ValidationException.class, () -> fc.inMemoryFilmStorage.checkValidationFilm(updateFilm));
        assertEquals(exception.getMessage(), exception.getMessage(), "продолжительность фильма должна быть положительной");
    }
}