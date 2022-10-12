package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    public final InMemoryUserStorage inMemoryUserStorage;

    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public User addFriend(Integer id, Integer friendId) {
        if (inMemoryUserStorage.getUsers().containsKey(id) ||
                inMemoryUserStorage.getUsers().containsKey(friendId)) {

            Set<Integer>friends = new HashSet<>();
            Set<Integer>friends2 = new HashSet<>();

            User user = inMemoryUserStorage.getUsers().get(id);
            Set<Integer> temp = user.getFriends();
            friends.add(friendId);
            user.setFriends(mergeSets(temp, friends));

            User user2 = inMemoryUserStorage.getUsers().get(friendId);
            Set<Integer> temp2 = user2.getFriends();
            friends2.add(id);
            user2.setFriends(mergeSets(temp2, friends2));
            return user;
        }
        throw new ValidationException("Пользователя с этим номером не существует");
    }

    public User deleteFriend(Integer id, Integer friendId) {
        if (inMemoryUserStorage.getUsers().containsKey(id) ||
                inMemoryUserStorage.getUsers().containsKey(friendId)) {

            User user = inMemoryUserStorage.getUsers().get(id);
            Set<Integer> temp = user.getFriends();
            temp.remove(friendId);
            user.setFriends(temp);

            User user2 = inMemoryUserStorage.getUsers().get(friendId);
            Set<Integer> temp2 = user.getFriends();
            temp2.remove(id);
            user2.setFriends(temp2);
            return user;
        }
        throw new ValidationException(" Пользователя с этим номером не существует");
    }


    public Set<Integer> findAllfriends(Integer id) {
        if (inMemoryUserStorage.getUsers().containsKey(id)) {
            User user = inMemoryUserStorage.getUsers().get(id);
            return user.getFriends();
        }
        throw new ValidationException("Пользователя с этим номером не существует");
    }


    public Set<Integer> findCommonFriends(Integer id, Integer otherId) {
        if (inMemoryUserStorage.getUsers().containsKey(id) ||
                inMemoryUserStorage.getUsers().containsKey(otherId)) {
            User user = inMemoryUserStorage.getUsers().get(id);
            User userOther = inMemoryUserStorage.getUsers().get(otherId);
            Set<Integer> userfriends = new HashSet<>();
            Set<Integer> userOtherfriends = new HashSet<>();
            userfriends = user.getFriends();
            userOtherfriends = userOther.getFriends();
            Set<Integer> common = findCommonElements(userfriends, userOtherfriends);
            return common;
        }
        throw new ValidationException("Пользователя с этим номером не существует");
    }

    private static <T> Set<T> findCommonElements(Set<T> first, Set<T> second) {
        Set<T> common = new HashSet<>(first);
        common.retainAll(second);
        return common;
    }



    public static<T> Set<T> mergeSets(Set<T> a, Set<T> b)
    {
        return Stream.of(a, b)
                .flatMap(x -> x.stream())
                .collect(Collectors.toSet());
    }

}




