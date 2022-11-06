package ru.yandex.practicum.filmorate.storage.user;


import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmUserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Component("UserDaoImpl")
public class UserDaoImpl implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Collection<User> getAllUsers() {
        final String sql = "Select * from USERS_TABLE";
        Collection<User> users = new ArrayList<>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        while (rs.next()) {
            User user = new User(
                    rs.getInt("USER_ID"),
                    rs.getString("email"),
                    rs.getString("login"),
                    rs.getString("name"),
                    rs.getDate("Birthday").toLocalDate());
            user.setFriends((Set<User>) getUserFriends( rs.getInt("USER_ID")));
            users.add(user);
        }
        return users;
    }

    @Override
    public User addUser(User user) {
        String sqlQuery = "insert into USERS_TABLE(EMAIL,LOGIN,NAME,Birthday) " +
                "values (?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());
        return user;
    }

    @Override
    public void deleteUser(Integer idUser) {
        String sqlQuery = "delete from USERS_TABLE where USER_ID = ?";
        jdbcTemplate.update(sqlQuery, idUser);
    }

    @Override
    public User changeUser(User user) {
        if (user.getId() == null) {
            throw new ValidationException("Отсутствует id пользователя");
        }

            String sqlQuery = "update USERS_TABLE set " +
                    "EMAIL = ?, LOGIN = ?, NAME = ?,Birthday = ?" +
                    "where USER_ID = ?";
            jdbcTemplate.update(sqlQuery,
                    user.getEmail(),
                    user.getLogin(),
                    user.getName(),
                    user.getBirthday(),
                    user.getId());
            deleteFriends(user);
            insertFriendsip(user);
            return user;
        }

    public Optional<User> findUserById(Integer id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from USERS_TABLE where USER_ID = ?", id);
        if (userRows.next()) {
            User user = new User(
                    userRows.getInt("USER_ID"),
                    userRows.getString("email"),
                    userRows.getString("login"),
                    userRows.getString("name"),
                    userRows.getDate("Birthday").toLocalDate());
            user.setFriends((Set<User>) getUserFriends(user.getId()));
            log.info("Найден пользователь: {} {}");
            return Optional.of(user);
        } else {
            log.info("Пользователь с идентификатором {} не найден.", id);
            return Optional.empty();
        }
    }

    @Override
    public void addInFriend(long userId, long friendId) {
        User friend = findUserById((int) friendId).get();
        User user = findUserById((int) userId).get();
        user.addFriend(friend);
        changeUser(user);
    }


    private void insertFriendsip(User user) {
        if (user.getFriends().isEmpty()) {
            return;
        }
        String sql = "insert into FRIENDSHIP (FRIEND_ID, USER_ID) values (?, ?)";

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            for (User friend : user.getFriends()) {
                ps.setLong(1, friend.getId());
                ps.setLong(2, user.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

@Override
    public Collection<User> getUserFriends(Integer id) {
        final String sqlCnt = "SELECT COUNT(*) From USERS WHERE USER_ID=?";


        final String sql = "SELECT * From USERS_TABLE where USER_ID IN (SELECT FRIEND_ID FROM FRIENDSHIP where USER_ID = ?)";
        Collection<User> friends = new HashSet<>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, id);
        while (rs.next()) {
            friends.add(new User( // Long id, @Valid String email, @Valid String login, String name, LocalDate birthday
                    rs.getInt("USER_ID"),
                    rs.getString("EMAIL"),
                    rs.getString("LOGIN"),
                    rs.getString("NAME"),
                    rs.getDate("BIRTHDAY").toLocalDate()
            ));
        }
        return friends;
    }


    @Override
    public void addFriends(Integer userId, Integer friendId) {

    }


    private void deleteFriends(User user) {
        final String sql = "DELETE FROM FRIENDSHIP where USER_ID = ?";
        jdbcTemplate.update(sql, user.getId());
    }


}