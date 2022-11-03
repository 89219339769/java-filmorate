package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface FilmStorage {
    Film addFilm(Film film);
    void deleteFilm(Integer idFilm);
    Film changeFilm(Film film);
    Collection<Film> getAllFilms();
    Film findFilmById(int idFilm);

}
