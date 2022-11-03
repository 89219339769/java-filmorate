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

}




/*
    public Film changeFilm(Film film) {
        checkValidationFilm(film);
        return inMemoryFilmStorage.changeFilm(film);
    }

    public List<Film> getAllFilms() {
        return inMemoryFilmStorage.getAllFilms();
    }

    public Film findFilmById(int idFilm) {
        return inMemoryFilmStorage.findFilmById(idFilm);
    }

    public void deleteFilm(Integer idFilm) {
        inMemoryFilmStorage.deleteFilm(idFilm);
    }


    public void addLike(int idUser, int idFilm) {
        Film film = inMemoryFilmStorage.findFilmById(idFilm);
        User user = userService.findUserById(idUser).get();
        film.setLike(user.getId());
    }

    public void deleteLike(int idFilm, int idUser) {
        Film film = inMemoryFilmStorage.findFilmById(idFilm);
        User user = userService.findUserById(idUser).get();
        film.getLike().remove(user.getId());
    }

    public List<Film> bestFilmByLike(Integer count) {
        return inMemoryFilmStorage.getAllFilms().stream()
                .sorted((o1, o2) -> {
                    int result = Integer.valueOf(o1.getLike().size()).compareTo(Integer.valueOf(o2.getLike().size()));
                    return result * -1;
                }).limit(count)
                .collect(Collectors.toList());
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
}

 */
