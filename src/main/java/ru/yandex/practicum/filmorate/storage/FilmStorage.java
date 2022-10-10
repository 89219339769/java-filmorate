package ru.yandex.practicum.filmorate.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface FilmStorage {
    @PostMapping()
    public  Film createFilm(@Valid @RequestBody Film film) ;
    public Film updateFilm(@Valid @RequestBody Film film) ;

    public List<Film> getAllFilms();


    public void validateFilmPut(Film film);
    public void validateDateOfReliseFilm(Film film);

    public void validateFilmId(Film film);

    public void validateFilmCreate(Film film);

}
