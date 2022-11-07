package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController("")
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Long id){
        return  userService.findUserById(id);
    }

    @GetMapping
    public Collection<User> allUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public @Valid User addUser(@Valid @RequestBody User user){
        userService.addUser(user);
        return user;
    }


    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
    }


    @ResponseBody
    @PutMapping
    public User changeUser(@Valid @RequestBody User user) throws SQLException {
        return  userService.changeUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriends(@PathVariable("id") long userId,
                                                   @PathVariable("friendId") long friendId) throws SQLException {
        userService.addFriend(userId, friendId);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") int idUser, @PathVariable("otherId") int idOther){
        return userService.getCommonFriends(idUser, idOther);
    }
}
