package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
        User addUser(User user);

        User findUserById(Long id);

        List<User> findAllUsers();

        User updateUser(User user);

        List<User> findCommonFriends(Long id, Long otherId);

        List<User> findUserFriends(Long id);

        boolean saveFriend(Long id, Long friendId);

        boolean deleteFriend(Long id, Long friendId);
}

