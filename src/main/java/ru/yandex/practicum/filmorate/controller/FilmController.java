package ru.yandex.practicum.filmorate.controller;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    @RestController
    @RequestMapping("/films")
    public class FilmController {
        protected final Map<Integer, Film> films = new HashMap<>();
        protected int id = 1;
        private static final Logger log = LoggerFactory.getLogger(FilmController.class);

        @PostMapping()
        public Film createFilm(@Valid @RequestBody Film film) {
            validateFilmCreate(film);
            validateDateOfReliseFilm(film);
            film.setId(id++);
            films.put(film.getId(), film);
            log.info("Фильм с id {} добавлен ", film.getId());
            return film;
        }

        @PutMapping()
        public Film updateFilm(@Valid @RequestBody Film film) {
            validateFilmPut(film);
            validateDateOfReliseFilm(film);
            if (film.getId()<0){
               throw new ValidationException("Id Фильма не может быть отрицательным ");}
            films.put(film.getId(), film);
            log.info("Фильм с id {} обновлён", film.getId());
            return film;
        }

        @GetMapping()
        public List<Film> getAllFilms() {
            return new ArrayList<>(films.values());
        }

        public void validateFilmCreate(Film film) {
            if (films.containsKey(film.getId())) {
                throw new ValidationException("Фильм - " + film.getName() + " c id - " + film.getId() + " уже существует");
            }
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
    }





















/*    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
   // private final List<Film> films = new ArrayList<>();
    public static Map<Integer, Film> films = new HashMap<>();
    int id =1;
    @GetMapping


    public  Map<Integer, Film> findAll() {
        log.info(" Количество фильмов: " + films.size());
        return films;
    }

    @PostMapping
    public @Valid Film create(@Valid @RequestBody Film film) {




        if (film.getName().isBlank()) {
            log.info(" Название фильма не может быть пустым.");
            throw new AmptyNameException(" Название фильма не может быть пустым.");
        }
        StringBuilder str = new StringBuilder();

        str.append(film.getDescription());
        if (str.length() >  200) {
            log.info("Описание фильма не может  быть больее 200 символов.");
            throw new LengthFilmException("Описание фильма не может быть больше 200 символов.");
        }
        if (film.getDuration().isNegative()) {
            log.info(" Продолжительность не может быть отрицательной.");
            throw new NegativeDurationException("Продолжительность не может быть отрицательной.");
        }

        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.info("Дата релиза фильм не должна быть ранее 28 декабря 1985 года .");
            throw new DateException("Дата релиза фильм не должна быть ранее 28 декабря 1985 года .");
        }

        if(films.containsKey(film.getId())){
            throw new ValidationException("Фильм - " + film.getName() + " c id - " + film.getId() + " уже есть в базе");
        }

        if(films.containsKey(film.getId())){
            throw new ValidationException("Фильм - " + film.getName() + " c id - " + film.getId() + " уже есть в базе");
        }

            film.setId(id++);
            films.put(film.getId(), film);
            log.info("Фильм с id {} добавлен", film.getId());
            return film;
        }


    @PutMapping
    public @Valid Film put(@Valid @RequestBody Film film) {

        if (film.getId() < 0) {
            log.info("Id должен быть положитнльным.");
            throw new AmptyListException("Список фильмов пустой, нельзя обновить.");
        }

        //   for (int i = 0; i < films.size(); i++) {
        //    int temp = film.getId();
        //    if (films.get(i).getId() == temp) {
        //       films.remove(i);
        if (!films.containsKey(film.getId())) {

            film.setId(id++);
            films.put(film.getId(), film);
            log.info("Фильм с id {} добавлен", film.getId());
            return film;

        }
        return film;
    }
    */
