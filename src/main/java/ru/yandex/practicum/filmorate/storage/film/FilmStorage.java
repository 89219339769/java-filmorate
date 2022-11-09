package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    Film addFilm(Film film);

    Film findFilmById(Long id);

    List<Film> findAllFilms();

    Film updateFilm(Film film);

    boolean saveLike(Long id, Long userId);

    List<Film> findPopularFilms(Integer count);

    boolean deleteLike(Long id, Long userId);
}

