package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Mpa;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MpaDao {
    private final JdbcTemplate jdbcTemplate;


    public MpaDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public Optional<Mpa> getById(int id) {
        final String sql = "SELECT * FROM TABLE_MPA where MPA_ID = ?";
        return jdbcTemplate.queryForStream(sql, (rs, rowNum) ->
                new Mpa(rs.getInt(1), rs.getString(2)), id).findFirst();
    }


    public List<Mpa> getAll() {
        final String sql = "SELECT * FROM TABLE_MPA ORDER BY 1 asc ";

        return jdbcTemplate.queryForStream(sql, (rs, rowNum) ->
                        new Mpa(rs.getInt(1), rs.getString(2)))
                .sorted((o1, o2) -> o1.getId() < o2.getId() ? -1 : 1)
                .collect(Collectors.toList());
    }
}