package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Slf4j
@Service
public class FilmService {
    private static final LocalDate MIN_DATE_START_RELEASE = LocalDate.parse("1895-12-28");

    private UserStorage userstorage;
    private FilmStorage filmstorage;
    private final UserService userService;


    @Autowired
    public FilmService(@Qualifier("FilmDaoImpl") FilmStorage filmstorage, @Qualifier("UserDaoImpl") UserStorage userstorage, UserService userService) {
        this.userstorage = userstorage;
        this.filmstorage = filmstorage;
        this.userService = userService;
    }

    public Film addFilm(Film film) {
        checkValidationFilm(film);
        return filmstorage.addFilm(film);
    }

    public void addLike(Long userId, Long filmId) {
        User  user = userstorage.findUserById( userId).get();
        Film film = (Film) findFilmById(filmId).get();
        film.addLike(user);
        changeFilm(film);
    }


    public void removeLike(Long filmId, Long userId) {

        Film film = (Film) filmstorage.findFilmById(filmId).get();
        User user = userService.findUserById(userId).get();
        film.removeLike(user);
        filmstorage.changeFilm(film);
        log.info("User: was like film: {}", user, film);
    }


    public boolean checkValidationFilm(Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(MIN_DATE_START_RELEASE)) {
            log.info("Ошибка валидации: дата релиза — раньше 28 декабря 1895 года");
            throw new ValidationException("дата релиза раньше 28 декабря 1895 года");
        } else if (film.getDuration() < 0) {
            log.info("Ошибка валидации: продолжительность фильма отрицательная");
            throw new ValidationException("продолжительность фильма должна быть положительной");
        }
        return true;
    }



    public Collection<Film> getAllFilms() {
        return filmstorage.getAllFilms();
    }

    public void deleteFilm(Integer idFilm) {
        filmstorage.deleteFilm(idFilm);
    }

    public Optional<Object> findFilmById(Long idFilm) {
        return  filmstorage.findFilmById(idFilm);
    }

    public Film changeFilm(Film film) {
        checkValidationFilm(film);
        return filmstorage.changeFilm(film);
    }
}



