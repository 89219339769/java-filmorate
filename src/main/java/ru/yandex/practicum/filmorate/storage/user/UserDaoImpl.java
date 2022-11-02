package ru.yandex.practicum.filmorate.storage.user;


import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
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


    public List<User> getAllUsers() {
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
        return (List<User>) users;
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

    }

    @Override
    public User changeUser(User user) {
        return null;
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