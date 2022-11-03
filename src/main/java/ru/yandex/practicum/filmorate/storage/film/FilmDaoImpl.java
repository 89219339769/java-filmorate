package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component("FilmDaoImpl")
public class FilmDaoImpl implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


   /*
    @Override
    public Film addFilm(Film film) {
        String sqlQuery = "insert into TABLE_FILMS(NAME,DESCRIPTION,RELEASE_DATE,duration,MPA_ID) " +
                "values (?, ?, ?, ?,?)";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getMpa().getId());
        return film;
    }



*/
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
    public void deleteFilm(Integer idFilm) {

    }

    @Override
    public Film changeFilm(Film film) {
        return null;
    }

    @Override
    public List<Film> getAllFilms() {
        return null;
    }

    @Override
    public Film findFilmById(int idFilm) {
        return null;
    }
}
