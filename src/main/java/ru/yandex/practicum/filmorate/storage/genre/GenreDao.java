package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Genre;


import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class GenreDao {

    private static final String SELECT_ALL = "select * from TABLE_GENRES";
    private static final String SELECT_BY_ID = "select * from TABLE_GENRES where GENRES_ID =  ?";
    private static final String ADD_GENRE = "insert into TABLE_GENRES (NAME) values (?)";
    private static final String UPDATE_BY_ID = "update TABLE_GENRES set NAME = ? where GENRES_ID = ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Collection<Genre> findAll() {
        return jdbcTemplate.queryForStream(SELECT_ALL,
                (rs, rowNum) -> new Genre(rs.getInt("GENRES_ID"), rs.getString("name"))).collect(Collectors.toList());
    }


    public Genre findById(Integer id) {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(SELECT_BY_ID, id);
        if (rs.next()) {
            return (new Genre(rs.getInt(1),
                    rs.getString(2)));
        }
       return  null;
    }


    public void deleteAllByFilmId(Long filmId) {
        final String sql = "DELETE FROM FILMS_GENRES where GENRE_ID = ?";
        jdbcTemplate.update(sql, filmId);
    }


    public Optional<Genre> create(Genre genre) {
        if (jdbcTemplate.update(ADD_GENRE, genre.getName()) != 1) {
            return Optional.empty();
        } else {
            return Optional.of(genre);
        }
    }


    public Optional<Genre> update(Genre genre) {
        if (jdbcTemplate.update(UPDATE_BY_ID, (genre.getId())) != 1) {
            return Optional.empty();
        } else {
            return Optional.of(genre);
        }
    }

}