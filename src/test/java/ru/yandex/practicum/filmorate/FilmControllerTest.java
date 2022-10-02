package ru.yandex.practicum.filmorate;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmControllerTest {
    private FilmController uc;

    private Film getFilm() {
        return Film.builder()
                .id(1)
                .description("\"Фильмов много — и с каждым годом становится всё больше. \" +\n" +
                        "  \"Чем их больше, тем больше разных оценок. \" +\n" +
                        " \"Чем больше оценок, тем сложнее сделать выбор. Однако не время сдаваться!\" +\n" +
                        " \" Вы напишете бэкенд для сервиса, который будет работать с фильмами и оценками\" +\n" +
                        " \" пользователей, а также возвращать топ-5 фильмов, рекомендованных к просмотру.\" +\n" +
                        "  \" Теперь ни вам, ни вашим друзьям не придётся долго размышлять, что посмотреть \" +\n" +
                        "  \"вечером.\"")
                .name("")
                .releaseDate(LocalDate.of(1721, 1, 1))
                .duration(-90)
                .build();
    }

    @Test
    public void createFilmWithInvalidRelisDate() {
        uc = new FilmController();
        Film film = getFilm();
        assertThrows(ValidationException.class, () -> uc.createFilm(film));
    }


    @Test
    public void createFilmWithEmptyName() {
        uc = new FilmController();
        Film film = getFilm();
        assertThrows(ValidationException.class, () -> uc.createFilm(film));
    }

    @Test
    public void createFilmWithWrongDescription() {
        uc = new FilmController();
        Film film = getFilm();
        assertThrows(ValidationException.class, () -> uc.createFilm(film));
    }


    @Test
    public void createFilmWithNegativeDuration() {
        uc = new FilmController();
        Film film = getFilm();
        assertThrows(ValidationException.class, () -> uc.createFilm(film));
    }
}