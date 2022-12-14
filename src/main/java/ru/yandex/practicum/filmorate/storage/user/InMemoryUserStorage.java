package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmUserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private Integer globalIdUser = 1;

    private Integer createNextId() {return globalIdUser++;
    }

    @Override
    public User addUser(User user) {
            user.setId(createNextId());
            users.put(user.getId(), user);
            log.info("Успешное добавление пользователя");
            return user;
    }

    @Override
    public void deleteUser(Integer idUser) {
        users.remove(idUser);
    }

    @Override
    public User changeUser(User user) {
        if (user.getId() == null) {
            throw new ValidationException("Отсутствует id пользователя");
        }
        if (!users.containsKey(user.getId())) {
            throw new FilmUserNotFoundException(String.format("Пользователя с id %s нет", user.getId()));
        }
            users.put(user.getId(), user);
            log.info("Успешное изменение пользователя");
        return user;
    }

    public List<User> getAllUsers() {
        return users.values().stream().collect(Collectors.toList());
    }

    public User findUserById(Integer id) {
        if (users.get(id) == null) {
            throw new FilmUserNotFoundException("Нет такого пользователя");
        }
        return users.get(id);
    }
}





