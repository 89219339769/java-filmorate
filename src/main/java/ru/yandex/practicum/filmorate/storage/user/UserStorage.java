package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserStorage {
        Collection<User> getAllUsers();
        User addUser(User user);
        void deleteUser(Integer idUser);
        User changeUser(User user);
        Optional<User> findUserById(Long id);



        void addFriend(long userId, long friendId);

        Collection<User> getUserFriends(Integer id);


}



