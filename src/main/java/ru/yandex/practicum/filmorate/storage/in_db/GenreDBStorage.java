package ru.yandex.practicum.filmorate.storage.in_db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class GenreDBStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;
    private static final String ADD_FILM_GENRE = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";
    private static final String DELETE_FILM_FROM_FILM_GENRE = "DELETE FROM film_genre WHERE film_id = ?";
    private static final String GET_ALL = "SELECT * FROM genre ORDER BY genre_id";
    private static final String GET_BY_ID = "SELECT * FROM genre WHERE genre_id = ?";
    private static final String GET_GENRES_BY_FILM_ID = "SELECT * FROM genre WHERE genre_id IN " +
            "(SELECT genre_id FROM film_genre WHERE film_id = ?)";


    @Autowired
    public GenreDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFilm(long filmId, Set<FilmGenre> genres) {
        List<Object[]> batchArgsList = new ArrayList<>();
        Object[] objArray;
        for (FilmGenre genre : genres) {
            objArray = new Object[]{filmId, genre.getId()};
            batchArgsList.add(objArray);
        }
        jdbcTemplate.batchUpdate(ADD_FILM_GENRE, batchArgsList);
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

    @Override
    public List<FilmGenre> getGenresByFilm(long filmId) {
        return jdbcTemplate.query(GET_GENRES_BY_FILM_ID, (rs, rowNum) -> makeGenre(rs), filmId);
    }

    @Override
    public void deleteFilm(long filmId) {
        jdbcTemplate.update(DELETE_FILM_FROM_FILM_GENRE, filmId);
    }

    @Override
    public void updateFilm(long filmId, Set<FilmGenre> genres) {
        deleteFilm(filmId);
        addFilm(filmId, genres);
    }

    private FilmGenre makeGenre(ResultSet rs) throws SQLException {
        int id = rs.getInt("genre_id");
        String name = rs.getString("name");
        return new FilmGenre(id, name);
    }
}
