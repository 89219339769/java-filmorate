package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Optional;

public interface FilmStorage {
    Film addFilm(Film film);
    void deleteFilm(Integer idFilm);
    Film changeFilm(Film film);
    Collection<Film> getAllFilms();
    Optional<Object> findFilmById(int idFilm);
}
