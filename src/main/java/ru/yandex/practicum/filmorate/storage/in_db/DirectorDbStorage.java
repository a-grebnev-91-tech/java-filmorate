package ru.yandex.practicum.filmorate.storage.in_db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.FilmDirector;
import ru.yandex.practicum.filmorate.storage.DirectorStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class DirectorDbStorage implements DirectorStorage {
    private final JdbcTemplate jdbcTemplate;
    private static String INSERT_DIRECTOR = "INSERT INTO directors (name) VALUES (?)";
    private static String DELETE_DIRECTOR = "DELETE FROM directors WHERE director_id = ?";
    private static String GET_DIRECTOR = "SELECT * FROM directors WHERE director_id = ?";
    private static String GET_ALL_DIRECTOR = "SELECT * FROM directors";
    private static String UPDATE_DIRECTOR = "UPDATE directors SET name = ? WHERE director_id = ?";

    @Autowired
    public DirectorDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long create(FilmDirector director) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(INSERT_DIRECTOR, new String[]{"director_id"});
            stmt.setString(1, director.getName());
            return stmt;
        }, keyHolder );
        return keyHolder.getKey().longValue();
    }

    @Override
    public FilmDirector delete(long id) {
        FilmDirector toDelete = get(id);
        jdbcTemplate.update(DELETE_DIRECTOR, id);
        return toDelete;
    }

    @Override
    public FilmDirector get(long id) {
        List<FilmDirector> directors = jdbcTemplate.query(GET_DIRECTOR, (rs, rowNum) -> makeDirector(rs), id);
        if (directors.isEmpty()) {
            throw new NotFoundException(String.format("Director with id %d isn't exist", id));
        }
        return directors.get(0);
    }

    @Override
    public List<FilmDirector> getAll() {
        return jdbcTemplate.query(GET_ALL_DIRECTOR, (rs, rowNum) -> makeDirector(rs));
    }

    @Override
    public boolean update(FilmDirector director) {
        return jdbcTemplate.update(UPDATE_DIRECTOR, director.getName(), director.getId()) > 0;
    }

    private FilmDirector makeDirector(ResultSet rs) throws SQLException {
        return new FilmDirector(rs.getInt("director_id"), rs.getString("name"));
    }
}
