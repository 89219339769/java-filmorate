package ru.yandex.practicum.filmorate;

<<<<<<< HEAD
import org.junit.jupiter.api.BeforeEach;
=======
>>>>>>> controllers-films-users
import org.junit.jupiter.api.Test;
import org.springframework.beans.PropertyBatchUpdateException;
import org.springframework.beans.factory.parsing.Problem;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertEquals;

public class FilmControllerTest {
    private FilmController uc;

    private Film getFilm() {
        return Film.builder()
                .id(1)
                .description("Фильмов много — и с каждым годом становится всё больше.")
                .name("фильм")
                .releaseDate(LocalDate.of(1921, 1, 1))
                .duration(90)
                .build();
    }
<<<<<<< HEAD
    @BeforeEach
    public void beforeEach() {
        uc = new FilmController();
        Film film = getFilm();
        uc.createFilm(film);
    }

    @Test
    public void createFilmWithInvalidRelisDate() {

=======

    @Test
    public void createFilmWithInvalidRelisDate() {
        uc = new FilmController();
        Film film = getFilm();
        uc.createFilm(film);
>>>>>>> controllers-films-users
        Film updateFilm = getFilm();
        updateFilm.setReleaseDate(LocalDate.of(1721, 1, 1));
        RuntimeException exception;

        exception = assertThrows(ValidationException.class, () -> uc.validateDateOfReliseFilm(updateFilm));
        assertEquals(exception.getMessage(), exception.getMessage(), "Дата релиза фильм не должна быть ранее 28 декабря 1895 года .");
    }


    @Test
    public void createFilmWithWrongId() {
<<<<<<< HEAD
        Film updateFilm = getFilm();
        updateFilm.setId(1);
        RuntimeException exception;
        Film film = getFilm();
=======
        uc = new FilmController();
        Film film = getFilm();
        uc.createFilm(film);
        Film updateFilm = getFilm();
        updateFilm.setId(1);
        RuntimeException exception;

>>>>>>> controllers-films-users
        exception = assertThrows(ValidationException.class, () -> uc.validateFilmCreate(updateFilm));
        assertEquals(exception.getMessage(), exception.getMessage(), "Фильм - " + film.getName() + " c id - " + film.getId() + " уже существует");
    }


    @Test
    public void createFilmWithNegativeId() {
<<<<<<< HEAD
=======
        uc = new FilmController();
        Film film = getFilm();
        uc.createFilm(film);
>>>>>>> controllers-films-users
        Film updateFilm = getFilm();
        updateFilm.setId(-1);
        RuntimeException exception;

        exception = assertThrows(ValidationException.class, () -> uc.validateFilmId(updateFilm));
        assertEquals(exception.getMessage(), exception.getMessage(), "Id Фильма не может быть отрицательным ");
    }
<<<<<<< HEAD
=======


>>>>>>> controllers-films-users
}