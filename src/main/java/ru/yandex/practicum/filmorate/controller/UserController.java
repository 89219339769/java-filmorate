package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;


import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

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
        return inMemoryUserStorage.findAll();
    }



    @GetMapping("users/{id}/friends")
    public Set<Integer> findAllFriends(@PathVariable("id") Integer id) {
        return userService.findAllfriends(id);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public Set<Integer> findCommonFriends(@PathVariable("id")Integer id,@PathVariable("otherId") Integer otherId) {
        return userService.findCommonFriends(id,otherId);
    }


    @GetMapping("{id}")
    public User findUser(@PathVariable("id") Integer id) {
        return inMemoryUserStorage.findUserById(id);
    }


    @PostMapping("users/{id}/friends/{friendId}")
    public User addFriend(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId) {
        return userService.addFriend(id, friendId);
    }


    @DeleteMapping ("users/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable("id") Integer id,@PathVariable("friendId")Integer friendId) {
        return userService.deleteFriend(id,friendId);
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