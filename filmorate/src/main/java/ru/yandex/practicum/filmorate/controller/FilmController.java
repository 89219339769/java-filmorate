package ru.yandex.practicum.filmorate.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;





@RestController
@RequestMapping("/films")
public class FilmController {
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private final List<Film> films = new ArrayList<>();

    @GetMapping
    public List<Film> findAll() {
        log.info(" Количество фильмов: " + films.size());
        return films;
    }

    @PostMapping
    public void create(@Valid @RequestBody Film film) {
        if (film.getName().isBlank()) {
            log.info(" Название фильма не может быть пустым.");
            throw new AmptyNameException(" Название фильма не может быть пустым.");
        }
        StringBuilder str = new StringBuilder();

        str.append(film.getDescription());
        if (str.length() > 200) {
            log.info("Описание фильма не может  быть больее 200 символов.");
            throw new LengthFilmException("Описание фильма не может быть больше 200 символов.");
        }
        if (film.getDuration().isNegative()) {
            log.info("Продолжительность не может быть отрицательной.");
            throw new NegativeDurationException("Продолжительность не может быть отрицательной.");
        }

        if (film.getReleaseDate().isBefore(LocalDate.of(1985, 12, 28))) {
            log.info("Дата релиза фильм не должна быть ранее 28 декабря 1985 года .");
            throw new DateException("Дата релиза фильм не должна быть ранее 28 декабря 1985 года .");
        }
        log.info("добавлен фильм " + film);
        films.add(film);
    }

    @PutMapping
    public void put(@Valid @RequestBody Film film) {
        if (films.size() == 0) {
            log.info("Список фильмов пустой, нельзя обновить.");
            throw new AmptyListException("Список фильмов пустой, нельзя обновить.");
        }

        for (int i = 0; i < films.size(); i++) {
            int temp = film.getId();
            if (films.get(i).getId() == temp) {
                films.remove(i);
                films.add(film);
                log.info("Список фильмов обновлен");
            } else {
                log.info("в списке нет фильма с номером" + film.getId() + "используйте метод create");
            }
        }
    }
}