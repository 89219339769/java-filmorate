package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.HashSet;
import java.util.Set;

@Service
public class FilmService {
    public final InMemoryUserStorage inMemoryUserStorage;
    public final InMemoryFilmStorage inMemoryFilmStorage;

    public FilmService(InMemoryUserStorage inMemoryUserStorage, InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }


    public Film addLike(Integer id, Integer userId) {
        if (inMemoryFilmStorage.getAllFilms().contains(id) ||
                inMemoryUserStorage.getUsers().containsKey(userId)) {
            Film film = inMemoryFilmStorage.getFilms().get(id);
            User user = inMemoryUserStorage.getUsers().get(userId);
            Set<User> likeTemp = film.getLike();
            if (film.getLike().contains(user)) {
                throw new ValidationException("этот пользователь уже поставил лайк'");
            }
            Set<User> temp = film.getLike();
            temp.add(user);
            film.setLike(temp);
            int temp1 = film.getLikes();
            film.setLikes(temp1 + 1);
            return film;
        }
        throw new ValidationException("Пользователя с этим номером не существует");
    }
}
