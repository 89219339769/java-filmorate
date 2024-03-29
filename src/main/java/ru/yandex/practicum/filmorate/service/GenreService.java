package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreDbStorage;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GenreService {
    private final GenreDbStorage genreDbStorage;



    public Genre findGenreById(Long id) {
        Genre genre = genreDbStorage.findGenreById(id);
        log.info("Найден жанр ", id);
        return genre;
    }

    public List<Genre> findFilmGenresByFilmId(Long id) {
        List<Genre> genres = genreDbStorage.findFilmGenresByFilmId(id);
        log.info("Найдены жанры фильма ", id);
        return genres;
    }

    public List<Genre> findAllGenres() {
        List<Genre> genres = genreDbStorage.findAllGenres();
        log.info("Все жанры найдены.");
        return genres;
    }
}
