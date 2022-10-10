package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;


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



    public UserController(UserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }
    public final UserStorage inMemoryUserStorage;

    @GetMapping
    public Collection<User> findAll() {
        return  inMemoryUserStorage.findAll();
    }

    @GetMapping("user/{id}")
    public User findUser(@PathVariable("id") Integer id) {
        return inMemoryUserStorage.findUserById(id);
    }



    @PostMapping
    public User create(@Valid @RequestBody User user) {
        return inMemoryUserStorage.create(user);
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) {

        return inMemoryUserStorage.put(user);
    }
}