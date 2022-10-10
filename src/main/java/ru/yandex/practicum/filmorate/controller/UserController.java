package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;


import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    public final UserStorage inMemoryUserStorage;
    public UserController(UserService userService, UserStorage inMemoryUserStorage) {
        this.userService = userService;
        this.inMemoryUserStorage = inMemoryUserStorage;
    }



    @GetMapping
    public Collection<User> findAll() {
        return  inMemoryUserStorage.findAll();
    }

    @GetMapping("user/{id}")
    public User findUser(@PathVariable("id") Integer id) {
        return inMemoryUserStorage.findUserById(id);
    }



    @PostMapping("users/{id}/friends/{friendId}")
    public User addFriend(@PathVariable("id") Integer id,@PathVariable("friendId")long friendId) {
return userService.addFriend(id,friendId);
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