package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmUserNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserDaoImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@Component("FilmDaoImpl")
public class FilmDaoImpl implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    UserDaoImpl userDaoImpl;
    @Autowired
    public FilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film addFilm(Film film) {
        String sqlQuery = "INSERT INTO table_films (name, description, release_date, duration,MPA_ID) " +
                "VALUES (?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sqlQuery, new String[]{"MPA_ID"});
            statement.setString(1, film.getName());
            statement.setString(2, film.getDescription());
            statement.setDate(3, Date.valueOf(film.getReleaseDate()));
            statement.setLong(4, film.getDuration());
            statement.setLong(5, film.getMpa().getId());
            return statement;
        }, keyHolder);

        film.setId((int) Objects.requireNonNull(keyHolder.getKey()).longValue());
        return film;

    }

    @Override
    public Collection<Film> getAllFilms() {
        Collection<Film> films = new ArrayList<>();
        SqlRowSet rs = jdbcTemplate.queryForRowSet("select f.FILM_ID, f.NAME, f.DESCRIPTION, f.RELEASE_DATE," +
                " f.DURATION, f.MPA_ID,m.NAME as MPA_NAME FROM table_films AS f JOIN table_mpa AS m ON m.mpa_id = f.mpa_id; ");
        while (rs.next()) {
            Film film = new Film(
                    rs.getInt("film_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDate("release_date").toLocalDate(),
                    (rs.getInt("duration")),
                    new Mpa(rs.getInt("MPA_ID"), rs.getString("MPA_NAME"))
            );
            films.add(film);
        }
        return films;
    }


    @Override
    public void deleteFilm(Integer idFilm) {
        String sqlQuery = "delete from TABLE_FILMS where FILM_ID = ?";
        jdbcTemplate.update(sqlQuery, idFilm);
    }


    private void deleteLikes(Film film) {
        final String sql = "DELETE FROM LIKES where USER_ID = ?";
        jdbcTemplate.update(sql, film.getId());
    }




    @Override
    public Optional<Object> findFilmById(int idFilm) {

        SqlRowSet userRows = jdbcTemplate.queryForRowSet("select f.FILM_ID, f.NAME, f.DESCRIPTION, f.RELEASE_DATE," +
                " f.DURATION, f.MPA_ID,m.NAME as MPA_NAME FROM table_films AS f JOIN table_mpa AS m ON m.mpa_id = f.mpa_id " +
                " where f.FILM_ID= ?", idFilm);

        // обрабатываем результат выполнения запроса
        if (userRows.next()) {
            Film film = new Film(
                    userRows.getInt("FILM_ID"),
                    userRows.getString("name"),
                    userRows.getString("description"),
                    userRows.getDate("release_date").toLocalDate(),
                    userRows.getInt("duration"),
                    new Mpa(userRows.getInt("MPA_ID"), userRows.getString("MPA_NAME")));
           film.setLikes((Set<User>)getFilmLikes(film.getId()));
            log.info("Найден фильм: {} {}");

            return Optional.of(film);
        } else {
            log.info("фильм с идентификатором {} не найден.", idFilm);
            return Optional.empty();
        }
    }

        public Collection<User> getFilmLikes(Integer id) {

            final String sql = "SELECT * From USERS_TABLE where USER_ID IN (SELECT FILM_ID FROM LIKES where USER_ID = ?)";
            Collection<User> likes = new HashSet<>();
            SqlRowSet rs = jdbcTemplate.queryForRowSet(sql, id);
            while (rs.next()) {
                likes.add(new User( // Long id, @Valid String email, @Valid String login, String name, LocalDate birthday
                        rs.getInt("USER_ID"),
                        rs.getString("EMAIL"),
                        rs.getString("LOGIN"),
                        rs.getString("NAME"),
                        rs.getDate("BIRTHDAY").toLocalDate()
                ));
            }
            return  likes;
        }









/*
    @Override
    public Film changeFilm(Film film) {
        if (film.getId() == null) {
            throw new ValidationException("Отсутствует id фильма");
        }

        List<Film> films = (List<Film>) getAllFilms();



        if (!films.contains(film)) {
            throw new FilmUserNotFoundException(String.format("Фильма с id  нет"));
        }


        String sqlQuery = " UPDATE TABLE_FILMS SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? " +
                " WHERE FILM_id = ?";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        return film;
    }

*/


    public Film changeFilm(Film film) {
        if (film.getId() == null) {
            throw new ValidationException("Отсутствует id фильма");
        }

        String sqlQuery = " UPDATE TABLE_FILMS SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? " +
                " WHERE FILM_id = ?";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());

          deleteLikes(film);
          insertLike(film);
        return film;

    }



    private void insertLike(Film film) {
        if (film.getLikes().isEmpty()) {
            return;
        }
        String sql = "insert into LIKES (FILM_ID, USER_ID) values (?, ?)";

        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            for (User like : film.getLikes()) {
                ps.setLong(1, like.getId());
                ps.setLong(2, film.getId());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





}








