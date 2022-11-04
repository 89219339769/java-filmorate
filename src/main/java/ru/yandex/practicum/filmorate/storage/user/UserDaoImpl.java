package ru.yandex.practicum.filmorate.storage.user;


import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmUserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
            users.add(new User(rs.getInt("USER_ID"),
                    rs.getString("EMAIL"),
                    rs.getString("LOGIN"),
                    rs.getString("NAME"),
                    rs.getDate("Birthday").toLocalDate()
            ));
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
        List<User> users;
        users = (List<User>) getAllUsers();
       User userTemp = users.get(user.getId());

          if (!users.contains( userTemp)) {
                throw new FilmUserNotFoundException(String.format("Пользователя с id %s нет", user.getId()));
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
        return user;
    }


    public Optional<User> findUserById(Integer id) {
        // выполняем запрос к базе данных.
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select * from USERS_TABLE where USER_ID = ?", id);

        // обрабатываем результат выполнения запроса
        if (userRows.next()) {
            User user = new User(
                    userRows.getInt("USER_ID"),
                    userRows.getString("email"),
                    userRows.getString("login"),
                    userRows.getString("name"),
                    userRows.getDate("Birthday").toLocalDate());

            log.info("Найден пользователь: {} {}");

            return Optional.of(user);
        } else {
            log.info("Пользователь с идентификатором {} не найден.", id);
            return Optional.empty();
        }
    }
}