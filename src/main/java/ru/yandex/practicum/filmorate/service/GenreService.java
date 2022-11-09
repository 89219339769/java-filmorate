package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreDao;


import java.util.Collection;
import java.util.Optional;

@Service
public class  GenreService {

    private final GenreDao genreDao;

    @Autowired
    public GenreService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }


    public Collection<Genre> findAll() {
        return genreDao.findAll();
    }


    public Genre findById(Integer id) {
        return genreDao.findById(id);
    }


    public void deleteAllByFilmId(Long filmId) {
        genreDao.deleteAllByFilmId(filmId);
    }


    public Optional<Genre> create(Genre genre) {
        return genreDao.create(genre);
    }


    public Optional<Genre> update(Genre genre) {
        return genreDao.update(genre);
    }

    public void updateAllByFilm(Film film) {
    }
}