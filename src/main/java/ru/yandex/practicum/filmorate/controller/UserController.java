package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.Collection;
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
    public Optional<User> getUser(@PathVariable Integer id){
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
    public User changeUser(@Valid @RequestBody User user){
        return  userService.changeUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addInFriends(@PathVariable("id") long userId,
                                                   @PathVariable("friendId") long friendId) throws SQLException {

        userService.addInFriend(userId, friendId);

    }
}
    /*
    public final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{id}")
    public User findUserById(@PathVariable Integer id){
        return userService.findUserById(id);
    }

    @GetMapping("/users")
    public List<User> allUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> findCommonFriends(@PathVariable("id") int idUser, @PathVariable("otherId") int idOther){
        return userService.findCommonFriends(idUser, idOther);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> findAllFriends(@PathVariable("id") Integer idUser){
        return userService.findAllFriends(idUser);
    }

    @PostMapping("/users")
    public @Valid User addUser(@Valid @RequestBody User user){
        userService.addUser(user);
        return user;
    }

    @ResponseBody
    @PutMapping("/users")
    public User changeUser(@Valid @RequestBody User user){
        return  userService.changeUser(user);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public void addFriends(@PathVariable("id") int idUser, @PathVariable("friendId") int idFriends){
        userService.addFriends(idUser, idFriends);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public void deleteFriends(@PathVariable("id") int idUser, @PathVariable("friendId") int idFriends){
        userService.deleteFriends(idUser, idFriends);
    }
}


    */