package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    public final  FilmStorage  inMemoryFilmStorage;
    private final FilmService filmService;

    public FilmController(FilmStorage inMemoryFilmStorage, FilmService filmService) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.filmService = filmService;
    }



    @PostMapping()
    public Film createFilm(@Valid @RequestBody Film film) {
        return  inMemoryFilmStorage.createFilm(film);

    }


    @PostMapping("films/{id}/like/{userId}")
    public  Film addLike(@PathVariable("id") Integer id, @PathVariable("userId") Integer friendId) {
        return filmService.addLike(id, friendId);
    }

    @DeleteMapping ("films/{id}/like/{userId}")
    public  Film deleteLike(@PathVariable("id") Integer id, @PathVariable("userId") Integer friendId) {
        return filmService.deleteLike(id, friendId);
    }



    @PutMapping()
    public Film updateFilm(@Valid @RequestBody Film film) {

        return inMemoryFilmStorage.updateFilm(film);
    }

    @GetMapping()
    public List<Film> getAllFilms() {

        return inMemoryFilmStorage.getAllFilms();
    }

    @GetMapping("film/{id}")
    public Film findFilm(@PathVariable("id") Integer id) {
        return inMemoryFilmStorage.findFilmById(id);
    }

}