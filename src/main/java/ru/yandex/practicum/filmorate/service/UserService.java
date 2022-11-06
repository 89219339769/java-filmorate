package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.FilmUserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;


import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    private UserStorage storage;

    @Autowired
    public UserService(@Qualifier("UserDaoImpl") UserStorage storage) {
        this.storage = storage;
    }

    public Optional<User> findUserById(Integer id) {
        return storage.findUserById(id);
    }

    public Collection<User> getAllUsers() {
        return storage.getAllUsers();
    }


    public User addUser(User user) {
        checkValidationUser(user);
        return storage.addUser(user);
    }

    public void deleteUser(Integer idUser) {
        storage.deleteUser(idUser);
    }


    public User changeUser(User user) throws SQLException {
        checkValidationUser(user);
        List<User> users;
          users = (List<User>) getAllUsers();
//
        Optional<User> res = Optional.ofNullable(users.get(user.getId()));
        if (res.isPresent()) {
            return res.get();
        }
         throw new FilmUserNotFoundException(String.format("Пользователя с id %s нет", user.getId()));
    }




    public boolean checkValidationUser(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("дата рождения не может быть в будущем");
            throw new ValidationException("дата рождения не может быть в будущем");
        }
        String temp = user.getLogin();
        if (temp.contains(" ")) {
            log.info("логин не млжет быть пустым или с пробелами.");
            throw new ValidationException("Некоректные данные ");
        }
        return true;
    }


    public void addFriend(long userId, long friendId) {
           storage.addFriend(userId, friendId);

    }

    public List<User> getCommonFriends(Integer id,Integer otherId) {
        Collection<User> first =  storage.getUserFriends(id);
        Collection<User> second =  storage.getUserFriends(otherId);
        return first.stream().filter(second::contains).collect(Collectors.toList());
    }

}
 /*



    //Добавляет друзей в список
    public void addFriends(int idUser, int idFriends){
        User user = inMemoryUserStorage.findUserById(idUser);
        User friend = inMemoryUserStorage.findUserById(idFriends);
        user.getFriends().add(idFriends);
        friend.getFriends().add(idUser);
    }

    //Удаляет друзей из списка
    public void deleteFriends(int idUser, int idFriends){
        inMemoryUserStorage.findUserById(idUser).getFriends().remove(idFriends);
        inMemoryUserStorage.findUserById(idFriends).getFriends().remove(idUser);
    }

    public List<User> findAllFriends(Integer idUser){
        List<User> friends = new ArrayList<>();
        User user = inMemoryUserStorage.findUserById(idUser);
        if (user.getFriends() != null){
            for (Integer id : user.getFriends()){
                friends.add(inMemoryUserStorage.findUserById(id));
            }
        }
        return friends;
    }

    public List<User> findCommonFriends(int idUser, int idOther){
        List<User> commonFriends = new ArrayList<>();
        User user = inMemoryUserStorage.findUserById(idUser);
        User otherUser = inMemoryUserStorage.findUserById(idOther);
        for (Integer friend : user.getFriends()) {
            if (otherUser.getFriends().contains(friend)) {
                commonFriends.add(inMemoryUserStorage.findUserById(friend));
            }
        }
        return commonFriends;
    }
    public boolean checkValidationUser(User user) {
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.warn("дата рождения не может быть в будущем");
            throw new ValidationException("дата рождения не может быть в будущем");
        }
        String temp = user.getLogin();
        if (temp.contains(" ")) {
            log.info("логин не млжет быть пустым или с пробелами.");
            throw new ValidationException("Некоректные данные ");
        }
        return true;
    }
}*/
