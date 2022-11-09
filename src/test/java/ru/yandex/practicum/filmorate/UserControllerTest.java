package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ObjectAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserControllerTest {
    private UserController userController;

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
        UserStorage inMemoryUserStorage = new InMemoryUserStorage();
        UserService userService = new UserService(inMemoryUserStorage);

        userController = new UserController(userService);
        User user = getUser();
        userController.saveUser(user);
    }

    @Test
    public void postUserWithInvalidBirsdayDate() {
        User updatedUser = getUser();
        updatedUser.setBirthday(LocalDate.of(2026, 1, 1));
        assertThrows(ObjectAlreadyExistException.class, () ->userController.userService.checkValidationUser(updatedUser), "дата рождения не может быть в будущем.");
    }

    @Test
    public void postUserWithInvalideLogin() {
        User updatedUser = getUser();
        updatedUser.setLogin("log in ");
        assertThrows(ObjectAlreadyExistException.class, () ->userController.userService.checkValidationUser(updatedUser));
    }

    @Test
    public void postUserWithInvalideDateOfBirth() {
        User updatedUser = getUser();
        updatedUser.setBirthday((LocalDate.of(3022, 1, 1)));
        assertThrows(ObjectAlreadyExistException.class, () -> userController.userService.checkValidationUser(updatedUser));
    }
}
