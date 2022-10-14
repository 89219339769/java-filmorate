package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.util.ArrayList;
import java.util.List;

// для работы с друзьями пользователя
@Service
public class UserService {
    private final UserStorage inMemoryUserStorage;

    @Autowired
    public UserService(UserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public User findUserById(Integer id) {
        return inMemoryUserStorage.findUserById(id);

    }

    public List<User> getAllUsers(){
        return inMemoryUserStorage.getAllUsers();
    }

    public User addUser(User user) {
        return inMemoryUserStorage.addUser(user);
    }

    public User changeUser(User user) {
        return inMemoryUserStorage.changeUser(user);
    }

    public void deleteUser(Integer idUser){
         inMemoryUserStorage.deleteUser(idUser);
    }

    public boolean checkValidationUser(User user) {
      return   inMemoryUserStorage.checkValidationUser(user);

    }

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

    //ищет общих друзей
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
}
