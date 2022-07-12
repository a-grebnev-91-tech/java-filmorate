package ru.yandex.practicum.filmorate.storage.in_db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.DataStorage;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component("filmDBStorage")
public class FilmDBStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film create(Film film) {
        String sqlQuery = "INSERT INTO films (description, duration, name, release_date, rating) " +
                "VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"film_id"});
            stmt.setString(1, film.getDescription());
            stmt.setInt(2, film.getDuration());
            stmt.setString(3, film.getName());
            stmt.setDate(4, Date.valueOf(film.getReleaseDate()));
            stmt.setString(5, film.getRating().getTitle());
            return stmt;
        }, keyHolder);
        long generatedId = keyHolder.getKey().longValue();
        film.setId(generatedId);
        updateFilmGenre(film);
        return film;
    }

    @Override
    public Film delete(long id) {
        Film filmToDelete = get(id);
        String sqlQuery = "DELETE FROM films WHERE film_id = ?";
        deleteFromFilmGenre(id);
        jdbcTemplate.update(sqlQuery, id);
        return filmToDelete;
    }

    @Override
    public Film get(long id) {
        String sqlFilmQuery = "SELECT * FROM films WHERE film_id = ?";
        List<Film> films = jdbcTemplate.query(sqlFilmQuery, (rs, rowNum) -> makeFilm(rs), id);
        if (films.size() < 1) {
            throw new NotFoundException("Film with id " + id + " isn't exist.");
        }
        return films.get(0);
    }

    @Override
    public Collection<Film> getAll() {
        String sqlQuery = "SELECT * FROM films";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeFilm(rs));
    }

    //todo not tested may be delete?
    @Override
    public Collection<Film> getSome(Collection<Long> ids) {
        String placeholders = String.join(",", Collections.nCopies(ids.size(), "?"));
        String sqlQuery = "SELECT * FROM films WHERE film_id IN " + placeholders;
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeFilm(rs));
    }

    @Override
    public List<Film> getTop(int count) {
        String sqlQuery = "SELECT * FROM films ORDER BY likes_count DESC LIMIT ?";
        return jdbcTemplate.query(sqlQuery, (rs, rowNum) -> makeFilm(rs), count);
    }

    @Override
    public boolean isExist(long id) {
        try {
            get(id);
            return true;
        } catch (NotFoundException ex) {
            return false;
        }
    }

    @Override
    public Film update(Film film) {
        long id = film.getId();
        if (isExist(id)) {
            String sqlFilmQuery = "UPDATE films SET description = ?, duration = ?, likes_count = ?, name = ?, " +
                    "release_date = ?, rating = ? WHERE film_id = ?";
            jdbcTemplate.update(sqlFilmQuery,
                    film.getDescription(),
                    film.getDuration(),
                    film.getLikeCount(),
                    film.getName(),
                    film.getReleaseDate(),
                    film.getRating().getTitle(),
                    film.getId());
            updateFilmGenre(film);
            return film;
        } else {
            throw new NotFoundException("Film with id " + id + " isn't exist.");
        }
    }

    private void deleteFromFilmGenre(long id) {
        String sqlDeleteQuery = "DELETE FROM film_genre WHERE film_id = ?";
        jdbcTemplate.update(sqlDeleteQuery, id);
    }

    private List<String> getGenresByFilmId(long id) {
        String sqlGenreQuery = "SELECT name FROM genre WHERE genre_id IN " +
                "(SELECT genre_id FROM film_genre WHERE film_id = ?)";
        return jdbcTemplate.queryForList(sqlGenreQuery, String.class, id);
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        long id = rs.getLong("film_id");
        String description = rs.getString("description");
        int duration = rs.getInt("duration");
        int likesCount = rs.getInt("likes_count");
        String name = rs.getString("name");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        MpaRating mpaRating = MpaRating.getByTitle(rs.getString("rating"));
        Set<FilmGenre> genres = getGenresByFilmId(id).stream().map(FilmGenre::getByTitle).collect(Collectors.toSet());
        return new Film(id, name, description, releaseDate, likesCount, duration, mpaRating, genres);
    }

    private void updateFilmGenre(Film film) {
        long filmId = film.getId();
        deleteFromFilmGenre(filmId);
        String sqlInsertQuery = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";
        List<Object[]> batchArgsList = new ArrayList<>();
        Object[] objArray;
        for (FilmGenre genre : film.getGenres()) {
            objArray = new Object[] {filmId, genre.getId()};
            batchArgsList.add(objArray);
        }
        jdbcTemplate.batchUpdate(sqlInsertQuery, batchArgsList);
    }
}
