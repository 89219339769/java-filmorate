package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import javax.validation.Valid;
import java.util.Collection;

@RestController("")
@Slf4j
public class FilmController {
    public final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping("/films")
    public Film addFilm(@Valid @RequestBody Film film) throws ValidationException {
        return filmService.addFilm(film);
    }

    @GetMapping("/films")
    public Collection<Film> allFilms(){
        return filmService.getAllFilms();
    }



    @DeleteMapping("/films/{id}")
    public void deleteFilm(@PathVariable Integer id){
        filmService.deleteFilm(id);
    }

}
/*
    @PutMapping("/films")
    public Film changeFilm(@Valid @RequestBody Film film) throws ValidationException{
        return filmService.changeFilm(film);
    }

    @GetMapping("/films")
    public List<Film> allFilms(){
        return filmService.getAllFilms();
    }

    @GetMapping("/films/{id}")
    public Film findFilmById(@PathVariable int id){
        return filmService.findFilmById(id);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId){
        filmService.addLike(userId, id);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId){
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/films/popular")
    public List<Film> bestFilmByLike(
            @RequestParam(value = "count", defaultValue = "10", required = false) Integer count){
        return filmService.bestFilmByLike(count);
    }

    @DeleteMapping("/films/{id}")
    public void deleteFilm(@PathVariable Integer id){
        filmService.deleteFilm(id);
    }
}

*/
