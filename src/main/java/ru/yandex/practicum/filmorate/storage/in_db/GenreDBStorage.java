package ru.yandex.practicum.filmorate.storage.in_db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class GenreDBStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;
    private static final String GET_ALL = "SELECT name FROM genre ORDER BY genre_id";
    private static final String GET_BY_ID = "SELECT name FROM genre WHERE genre_id = ?";

    @Autowired
    public GenreDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<FilmGenre> getAll() {
        return jdbcTemplate.query(GET_ALL, (rs, rowNum) -> makeGenre(rs));
    }

    @Override
    public FilmGenre get(int id) {
        List<FilmGenre> genres = jdbcTemplate.query(GET_BY_ID, (rs, rowNum) -> makeGenre(rs), id);
        if (genres.isEmpty()) {
            throw new NotFoundException(String.format("Genre with id %d isn't exist", id));
        }
        return genres.get(0);
    }

    private FilmGenre makeGenre(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        return FilmGenre.getByTitle(name);
    }
}
