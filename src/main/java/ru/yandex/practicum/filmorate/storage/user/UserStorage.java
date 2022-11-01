package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
        Collection<User> getAllUsers();
        User addUser(User user);
        void deleteUser(Integer idUser);
        User changeUser(User user);
        User findUserById(Integer id);
}



