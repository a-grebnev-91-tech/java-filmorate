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
import java.util.Collections;
import java.util.List;

@Component
public class DirectorDbStorage implements DirectorStorage {
    private final JdbcTemplate jdbcTemplate;
    private static final String DELETE_DIRECTOR = "DELETE FROM directors WHERE director_id = ?";
    private static final String DELETE_DIRECTOR_FROM_FILMS_DIRECTORS =
            "DELETE FROM films_directors WHERE director_id = ?";
    private static final String DELETE_FILM_FROM_FILMS_DIRECTORS =
            "DELETE FROM films_directors WHERE film_id = ?";
    private static final String GET_ALL_DIRECTOR = "SELECT * FROM directors";
    private static final String GET_DIRECTOR = "SELECT * FROM directors WHERE director_id = ?";
    private static final String GET_DIRECTOR_BY_FILM =
            "SELECT * FROM directors WHERE director_id = (SELECT director_id FROM films_directors WHERE film_id = ?)";
    private static final String GET_FILM_BY_DIRECTOR = "SELECT film_id FROM films_directors WHERE director_id = ?";
    private static final String GET_SOME_DIRECTORS_BY_ID = "SELECT * FROM directors WHERE director_id IN (%s)";
    private static final String INSERT_DIRECTOR = "INSERT INTO directors (name) VALUES (?)";
    private static final String INSERT_TO_FILMS_DIRECTORS =
            "INSERT INTO films_directors (film_id, director_id) VALUES (?, ?)";
    private static final String UPDATE_DIRECTOR = "UPDATE directors SET name = ? WHERE director_id = ?";

    @Autowired
    public DirectorDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addDirectorToFilm(long filmId, long directorId) {
        jdbcTemplate.update(INSERT_TO_FILMS_DIRECTORS, filmId, directorId);
    }

    @Override
    public long createDirector(FilmDirector director) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(INSERT_DIRECTOR, new String[]{"director_id"});
            stmt.setString(1, director.getName());
            return stmt;
        }, keyHolder );
        return keyHolder.getKey().longValue();
    }

    @Override
    public FilmDirector deleteDirector(long id) {
        FilmDirector toDelete = getDirector(id);
        deleteDirectorFromFilmsDirectors(id);
        jdbcTemplate.update(DELETE_DIRECTOR, id);
        return toDelete;
    }

    @Override
    public void deleteDirectorFromFilmsDirectors(long directorIds) {
        jdbcTemplate.update(DELETE_DIRECTOR_FROM_FILMS_DIRECTORS, directorIds);
    }

    @Override
    public void deleteFilmFromFilmsDirectors(long filmId) {
        jdbcTemplate.update(DELETE_FILM_FROM_FILMS_DIRECTORS, filmId);
    }

    @Override
    public FilmDirector getDirector(long id) {
        List<FilmDirector> directors = jdbcTemplate.query(GET_DIRECTOR, (rs, rowNum) -> makeDirector(rs), id);
        if (directors.isEmpty()) {
            throw new NotFoundException(String.format("Director with id %d isn't exist", id));
        }
        return directors.get(0);
    }

    @Override
    public List<FilmDirector> getDirectorsByFilm(long filmId) {
        List<FilmDirector> directors = jdbcTemplate.query(GET_DIRECTOR_BY_FILM, (rs, rowNum) -> {
            System.out.println(rowNum);
            return makeDirector(rs);
        }, filmId);
        return directors;
    }

    @Override
    public List<Long> getFilmsByDirectors(long directorId) {
        List<Long> filmIds = jdbcTemplate.queryForList(GET_FILM_BY_DIRECTOR, Long.class, directorId);
        if (filmIds.isEmpty()) {
            throw new NotFoundException(String.format("Director with id %d has no films", directorId));
        }
        return filmIds;
    }

    @Override
    public List<FilmDirector> getAllDirectors() {
        return jdbcTemplate.query(GET_ALL_DIRECTOR, (rs, rowNum) -> makeDirector(rs));
    }

    @Override
    public List<FilmDirector> getSomeDirectors(List<Long> directorsIds) {
        String placeholders = String.join(",", Collections.nCopies(directorsIds.size(), "?"));
        String sqlQuery = String.format(GET_SOME_DIRECTORS_BY_ID, placeholders);
        return jdbcTemplate.query(sqlQuery, directorsIds.toArray(),(rs, rowNum) -> makeDirector(rs));
    }

    @Override
    public boolean updateDirector(FilmDirector director) {
        return jdbcTemplate.update(UPDATE_DIRECTOR, director.getName(), director.getId()) > 0;
    }

    @Override
    public void updateFilmDirector(long filmId, List<Long> directorsIds) {
        deleteFilmFromFilmsDirectors(filmId);
        for (Long directorId : directorsIds) {
            addDirectorToFilm(filmId, directorId);
        }
    }

    private FilmDirector makeDirector(ResultSet rs) throws SQLException {
        long id = rs.getLong("director_id");
        String name = rs.getString("name");
        return new FilmDirector(id, name);
    }
}
