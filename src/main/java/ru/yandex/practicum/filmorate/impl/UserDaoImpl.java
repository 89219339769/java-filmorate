package ru.yandex.practicum.filmorate.impl;



import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Slf4j
@Component("UserDaoImpl")
public class UserDaoImpl  {
    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }




    public Collection<User> findAll() {
        final String sql = "Select * from users";

        Collection<User> users = new ArrayList<>();

        SqlRowSet rs = jdbcTemplate.queryForRowSet(sql);
        while (rs.next()) {
            users.add(new User(rs.getInt("USER_ID"),
                    rs.getString("EMAIL"),
                    rs.getString("LOGIN"),
                    rs.getString("NAME")

            ));
        }
        return users;
    }

    public Optional<User> findUserById(Integer id) {
        // выполняем запрос к базе данных.
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from users where Id = ?", id);

        // обрабатываем результат выполнения запроса
        if (userRows.next()) {
            User user = new User(
                    userRows.getInt(id),
                    userRows.getString("email"),
                    userRows.getString("login"),
                    userRows.getString("name"));

            log.info("Найден пользователь: {} {}");

            return Optional.of(user);
        } else {
            log.info("Пользователь с идентификатором {} не найден.", id);
            return Optional.empty();
        }
    }
}