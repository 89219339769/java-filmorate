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
    Set<Long> friends = new HashSet<>();
    // id  к кому  friendId кого
    public User addFriend(Integer id, Long friendId) {
        if (inMemoryUserStorage.getUsers().containsKey(id) ||
                inMemoryUserStorage.getUsers().containsKey(friendId)) {
            User user = inMemoryUserStorage.getUsers().get(id);
            friends.add(friendId);
            user.setFriends(friends);
            return user;
        }
        throw new ValidationException("Пользователя с этим номером не существует");
    }
}
