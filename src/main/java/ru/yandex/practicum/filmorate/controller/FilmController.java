package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {


    public FilmController(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    public final  InMemoryFilmStorage  inMemoryFilmStorage;









    @PostMapping()
    public Film createFilm(@Valid @RequestBody Film film) {
        return  inMemoryFilmStorage.createFilm(film);

    }

    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) {

        return inMemoryFilmStorage.updateFilm(film);
    }

    @GetMapping()
    public List<Film> getAllFilms() {

        return inMemoryFilmStorage.getAllFilms();
    }
}