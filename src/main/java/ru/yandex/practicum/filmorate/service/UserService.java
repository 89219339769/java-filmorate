package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.impl.UserDaoImpl;
import ru.yandex.practicum.filmorate.model.User;


import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserDaoImpl userDao;
    @Autowired
    public UserService(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    public Optional<User> findUserById(Integer id) {
        return userDao.findUserById(id);
    }

    public List<User> getAllUsers(){
        return userDao.getAllUsers();
    }


}


    /*
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
        checkValidationUser(user);
        return inMemoryUserStorage.addUser(user);
    }

    public User changeUser(User user) {
        checkValidationUser(user);
        return inMemoryUserStorage.changeUser(user);
    }

    public void deleteUser(Integer idUser){
         inMemoryUserStorage.deleteUser(idUser);
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
