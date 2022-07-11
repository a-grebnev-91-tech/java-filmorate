package ru.yandex.practicum.filmorate.storage.in_db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.DataStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Collection;

@Component("filmDBStorage")
public class FilmDBStorage implements DataStorage<Film> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film create(Film film) {
        String sqlQuery = "INSERT INTO films (description, duration, likes_count, name, release_date, rating) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"user_id"});
            stmt.setString(1, film.getDescription());
            stmt.setInt(2, film.getDuration());
            stmt.setInt(3, film.getLikesCount());
            stmt.setString(4, film.getName());
            stmt.setDate(5, Date.valueOf(film.getReleaseDate()));
            stmt.setString(6, film.getRating().getTitle());
            return stmt;
        }, keyHolder);
        long generatedId = keyHolder.getKey().longValue();
        film.setId(generatedId);
        return film;
    }

    @Override
    public Film delete(long id) {
        return null;
    }

    @Override
    public Film get(long id) {
        return null;
    }

    @Override
    public Collection<Film> getAll() {
        return null;
    }

    @Override
    public Collection<Film> getSome(Collection<Long> ids) {
        return null;
    }

    @Override
    public boolean isExist(long id) {
        return false;
    }

    @Override
    public Film update(Film baseEntity) {
        return null;
    }
}
