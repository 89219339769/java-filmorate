package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmUserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private static final LocalDate MIN_DATE_START_RELEASE = LocalDate.parse("1895-12-28");
    private Integer globalIdFilm = 1;

    private Integer createNextId() {
        return globalIdFilm++;
    }

    @Override
    public boolean checkValidationFilm(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(MIN_DATE_START_RELEASE)) {
            log.info("Ошибка валидации: дата релиза — раньше 28 декабря 1895 года");
            throw new ValidationException("дата релиза раньше 28 декабря 1895 года");
        } else if (film.getDuration() < 0) {
            log.info("Ошибка валидации: продолжительность фильма отрицательная");
            throw new ValidationException("продолжительность фильма должна быть положительной");
        }
        return true;
    }

    @Override
    public Film addFilm(Film film) {
        if (checkValidationFilm(film)) {
            film.setId(createNextId());
            films.put(film.getId(), film);
            log.info("Успешное добавление фильма: наименование - {}, символов в описании - {}, дата - {}, " +
                            "продолжительность - {}", film.getName(), film.getDescription().length()
                    , film.getReleaseDate(), film.getDuration());
        }
        return film;
    }

    @Override
    public void deleteFilm(Integer idFilm) {
        films.remove(idFilm);
    }

    @Override
    public Film changeFilm(Film film) {
        if (film.getId() == null) {
            throw new ValidationException("Отсутствует id параметр фильм");
        }


        if (!films.containsKey(film.getId())) {
            throw new FilmUserNotFoundException("фильма с этим номером нет, перезаписать невозможно");
        }


        if (checkValidationFilm(film)) {
            films.put(film.getId(), film);
            log.info("Успешное изменение фильма: наименование - {}, символов в описании - {}, дата - {}, " +
                            "продолжительность - {}", film.getName(), film.getDescription().length()
                    , film.getReleaseDate(), film.getDuration());
        }
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        return films.values().stream().collect(Collectors.toList());
    }

    @Override
    public Film findFilmById(int idFilm) {

        if (films.get(idFilm) == null) {
            throw new FilmUserNotFoundException("Нет такого фильма");
        }
        return films.get(idFilm);
    }
}