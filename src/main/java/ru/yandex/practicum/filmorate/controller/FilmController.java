package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/films/{id}")
    public Optional<Object> findFilmById(@PathVariable Long id){
        return filmService.findFilmById(id);
    }

    @PutMapping("/films")
    public Film changeFilm(@Valid @RequestBody Film film) throws ValidationException{
        return filmService.changeFilm(film);
    }

    @PutMapping("/films/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId){
        filmService.addLike(id,userId);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId){
        filmService.removeLike(id, userId);
    }



 //   @GetMapping("/popular")
 //   public Collection<Film> getPopular(@RequestParam(required = false, defaultValue = "10", name = "count") Integer count) {
  //      return filmService.getMostPopular(count);
  //  }

    @GetMapping("/films/popular")
    public List<Film> bestFilmByLike(
            @RequestParam(value = "count", defaultValue = "10", required = false) Integer count){
        return filmService.getMostPopular(count);
    }



}
/*




    @GetMapping("/films/popular")
    public List<Film> bestFilmByLike(
            @RequestParam(value = "count", defaultValue = "10", required = false) Integer count){
        return filmService.bestFilmByLike(count);
    }



*/
