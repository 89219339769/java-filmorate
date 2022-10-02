package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

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

    @Test
    public void postUserWithInvalidBirsdayDate() {
        uc = new UserController();
        User user = getUser();
        uc.create(user);
        User updatedUser = getUser();
        updatedUser.setBirthday(LocalDate.of(2026, 1, 1));
        assertThrows(ValidationException.class, () -> uc.create(updatedUser), "дата рождения не может быть в будущем.");
    }

    @Test
    public void postUserWithInvalideMail() {
        uc = new UserController();
        User user = getUser();
        uc.create(user);
        User updatedUser = getUser();
        updatedUser.setEmail("");
        assertThrows(ValidationException.class, () -> uc.create(updatedUser));
    }

    @Test
    public void postUserWithInvalideLogin() {
        uc = new UserController();
        User user = getUser();
        uc.create(user);
        User updatedUser = getUser();
        updatedUser.setLogin("");
        assertThrows(ValidationException.class, () -> uc.create(updatedUser));
    }

    @Test
    public void postUserWithInvalideDateOfBirth() {
        uc = new UserController();
        User user = getUser();
        uc.create(user);
        User updatedUser = getUser();
        updatedUser.setBirthday((LocalDate.of(3022, 1, 1)));
        assertThrows(ValidationException.class, () -> uc.create(updatedUser));
    }
}
