package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTest {
    private UserController uc;

    private User getUser() {
        return User.builder()
                .email("mail@mail.ru")
                .login("Test")
                .name("name")
                .birthday(LocalDate.of(2022, 1, 1))
                .build();
    }

    @BeforeEach
    public void beforeEach() {
        FilmStorage inMemoryFilmStorage = new InMemoryFilmStorage();
        UserStorage inMemoryUserStorage = new InMemoryUserStorage();
        UserService userService = new UserService(inMemoryUserStorage);

        uc = new UserController(inMemoryUserStorage,userService);
        User user = getUser();
        uc.addUser(user);
    }

    @Test
    public void postUserWithInvalidBirsdayDate() {
        User updatedUser = getUser();
        updatedUser.setBirthday(LocalDate.of(2026, 1, 1));
        assertThrows(ValidationException.class, () -> uc.inMemoryUserStorage.checkValidationUser(updatedUser), "дата рождения не может быть в будущем.");
    }

    @Test
    public void postUserWithInvalideLogin() {
        User updatedUser = getUser();
        updatedUser.setLogin("log in ");
        assertThrows(ValidationException.class, () ->uc.inMemoryUserStorage.checkValidationUser(updatedUser));
    }

    @Test
    public void postUserWithInvalideDateOfBirth() {
        User updatedUser = getUser();
        updatedUser.setBirthday((LocalDate.of(3022, 1, 1)));
        assertThrows(ValidationException.class, () -> uc.inMemoryUserStorage.checkValidationUser(updatedUser));
    }
}
