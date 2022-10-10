package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    public final InMemoryUserStorage inMemoryUserStorage;

    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }
    Set<Integer> friends = new HashSet<>();
    Set<Integer> friends2 = new HashSet<>();
    public User addFriend(Integer id, Integer friendId) {
        if (inMemoryUserStorage.getUsers().containsKey(id) ||
                inMemoryUserStorage.getUsers().containsKey(friendId)) {
            User user = inMemoryUserStorage.getUsers().get(id);
            friends.add(friendId);
            user.setFriends(friends);
            User user2 = inMemoryUserStorage.getUsers().get(friendId);
            friends2.add(id);
            user2.setFriends(friends2);
            return user;
        }
        throw new ValidationException("Пользователя с этим номером не существует");
    }

    public User deleteFriend(Integer id, Integer friendId) {
        if (inMemoryUserStorage.getUsers().containsKey(id) ||
                inMemoryUserStorage.getUsers().containsKey(friendId)) {
            User user = inMemoryUserStorage.getUsers().get(id);
            friends.remove(friendId);
            user.setFriends(friends);
            User user2 = inMemoryUserStorage.getUsers().get(friendId);
            friends2.remove(id);
            user2.setFriends(friends2);
            return user;
        }
        throw new ValidationException("Пользователя с этим номером не существует");
    }


}
