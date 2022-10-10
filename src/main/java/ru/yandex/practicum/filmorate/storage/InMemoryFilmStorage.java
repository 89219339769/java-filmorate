package ru.yandex.practicum.filmorate.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 1;
    private  final Logger log = LoggerFactory.getLogger(FilmController.class);


        @PostMapping()
        public Film createFilm(@Valid @RequestBody Film film) {
            validateFilmCreate(film);
            validateDateOfReliseFilm(film);
            validateFilmId(film);
            film.setId(id++);
            films.put(film.getId(), film);
            log.info("Фильм с id {} добавлен ", film.getId());
            return film;
        }

     @PutMapping()
     public Film updateFilm(@Valid @RequestBody Film film) {
         validateFilmPut(film);
         validateDateOfReliseFilm(film);
         validateFilmId(film);
         films.put(film.getId(), film);
         log.info("Фильм с id {} обновлён", film.getId());
         return film;
     }


     @GetMapping()
     public List<Film> getAllFilms() {

         return new ArrayList<>(films.values());
     }


     public void validateFilmPut(Film film) {
            if (!films.containsKey(film.getId())) {
                throw new ValidationException("Фильм - " + film.getName() + " c id - " + film.getId() + " не существует");
            }
        }

        public void validateDateOfReliseFilm(Film film) {
            if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
                log.info("Дата релиза фильм не должна быть ранее 28 декабря 1895 года .");
                throw new ValidationException("Дата релиза фильм не должна быть ранее 28 декабря 1895 года .");
            }
        }

        public void validateFilmId(Film film) {
            if (film.getId() < 0) {
                throw new ValidationException("Id Фильма не может быть отрицательным ");
            }
        }


     public void validateFilmCreate(Film film) {
         if (films.containsKey(film.getId())) {
             throw new ValidationException("Фильм - " + film.getName() + " c id - " + film.getId() + " уже существует");
         }
     }
    }



