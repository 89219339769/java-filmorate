package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;


import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        validateUserPost(user);
        String string = user.getEmail();
        char[] chArray = string.toCharArray();
        Character ch2 = '@';
        for (int i = 0; i < chArray.length; i++) {
            if (chArray[i] == ch2) {
                log.info("добавлен пользователь " + user);
                users.put(user.getId(), user);
                return user;
            }
        }
        log.info("Почта пользователя должна содержать символ @.");
        throw new ValidationException("Почта пользователя  должна содержать символ @.");
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) {

        validateUserPut(user);
        users.put(user.getId(), user);
        log.info("Список пользоваттелей обновлен.");
        return user;
    }

    public void validateUserPost(User user) {
        if (users.containsKey(user.getId())) {
            throw new ValidationException("Пользователь - " + user.getName() + " c id - " + user.getId() + " уже существует");
        }

        if (user.getId() == 0) {
            int temp = 1;
            user.setId(temp);
        }
        if (user.getBirthday().isAfter(ChronoLocalDate.from(LocalDateTime.now()))) {
            log.info("дата  рождения не может быть в будущем.");
            throw new ValidationException("дата рождения не может быть в будущем.");
        }
        String temp = user.getLogin();
        boolean temp1 = temp.contains(" ");
        if (user.getLogin().isBlank() || temp1) {
            log.info("почтовый адрес не может быть пустым или с пробелами.");
            throw new ValidationException("почтовый адрес не может быть пустым или с пробелами.");
        }
        String temp2 = user.getLogin();
        if (user.getName() == null || user.getId() == 0) {
            user.setName(temp2);
            log.info("логин будет использоваться как имя.");
        }
        if (user.getEmail().isBlank()) {
            log.info("почтовый адрес не может быть пустым.");
            throw new ValidationException("почтовый адрес не может быть пустым.");
        }
    }

    public void validateUserPut(User user) {
        if (!users.containsKey(user.getId())) {
            throw new ValidationException("Пользователь - " + user.getName() + " c id - " + user.getId() + " не существует");
        }

        if (user.getId() < 0) {
            throw new ValidationException("Id не может быть отрицательным.");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ValidationException("почтовый адрес не может быть пустым.");
        }
    }

}