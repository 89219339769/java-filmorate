package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmUserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;


@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();

    private Integer globalIdFilm = 1;

    private Integer createNextId() {
        return globalIdFilm++;
    }


    @Override
    public Optional<Object> addFilm(Film film) {
        film.setId(createNextId());
        films.put(film.getId(), film);
        log.info("Успешное добавление фильма: наименование - {}, символов в описании - {}, дата - {}, " +
                        "продолжительность - {}", film.getName(), film.getDescription().length(), film.getReleaseDate(), film.getDuration());
        return Optional.of(film);
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

            films.put(film.getId(), film);
            log.info("Успешное изменение фильма: наименование - {}, символов в описании - {}, дата - {}, " +
                            "продолжительность - {}", film.getName(), film.getDescription().length()
                    , film.getReleaseDate(), film.getDuration());

        return film;
    }

    @Override
    public Collection<Film> getAllFilms() {
        return films.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<Object> findFilmById(Long idFilm) {

        if (films.get(idFilm) == null) {
            throw new FilmUserNotFoundException("Нет такого фильма");
        }
        return Optional.ofNullable(films.get(idFilm));
    }

    @Override
    public List<Film> getMostPopular(Integer count) {
        return null;
    }

}