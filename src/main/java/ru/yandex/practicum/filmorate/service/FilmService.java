package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film addFilm(Film film) {
        Film savedFilm = filmStorage.addFilm(film);
        log.debug("Фильм  добавлен.", film.getName(), film.getId());
        return savedFilm;
    }

    public Film findFilmById(Long id) {
        Film film = filmStorage.findFilmById(id);
        if (film == null) {
            throw new ObjectNotFoundException(String.format("Фильм с id=%d не найден.", id));
        }
        log.debug("Фильм  найден.", film.getName(), film.getId());
        return film;
    }

    public List<Film> findAllFilms() {
        List<Film> films = filmStorage.findAllFilms();
        log.debug("Все фильмы найдены.");
        return films;
    }

    public Film updateFilm(Film film) {
        findFilmById(film.getId());
        Film updatedFilm = filmStorage.updateFilm(film);
        log.debug("Фильм  обновлён.", film.getId());
        return updatedFilm;
    }


    public boolean saveLike(Long id, Long userId) {
        boolean result = filmStorage.saveLike(id, userId);
        log.debug("Пользователь  поставил лайк.", userId, id);
        return result;
    }

    public List<Film> findPopularFilms(Integer count) {
        List<Film> popularFilms = filmStorage.findPopularFilms(count);
        log.debug(" фильмов возвращены.", count);
        return popularFilms;
    }

    public boolean deleteLike(Long id, Long userId) {
        if (!filmStorage.deleteLike(id, userId)) {
            throw new ObjectNotFoundException(String.format("Пользователь с id=%d не ставил лайк фильму с id=%d.", userId, id));
        }
        log.debug("Пользователь  удалил лайк ", userId, id);
        return true;
    }
}

