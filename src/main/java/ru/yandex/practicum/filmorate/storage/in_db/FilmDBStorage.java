package ru.yandex.practicum.filmorate.storage.in_db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.dto.repo.FilmRepoDto;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;
import ru.yandex.practicum.filmorate.model.film.Film;
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
    private static final String CREATE_FILM = "INSERT INTO films (description, duration, name, release_date, mpa_rating_id) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String DELETE_FILM = "DELETE FROM films WHERE film_id = ?";
    private static final String GET_FILM = "SELECT * FROM films WHERE film_id = ?";
    private static final String GET_ALL_FILMS = "SELECT * FROM films";
    private static final String GET_SOME_BY_ID = "SELECT * FROM films WHERE film_id IN (%s)";
    private static final String GET_TOP_FILMS = "SELECT * FROM films ORDER BY likes_count DESC LIMIT ?";
    private static final String UPDATE_FILM = "UPDATE films SET description = ?, duration = ?, likes_count = ?, " +
            "name = ?, release_date = ?, mpa_rating_id = ? WHERE film_id = ?";
    private static final String UPDATE_LIKES_COUNT = "UPDATE films f SET likes_count = (SELECT COUNT(fl.user_id) " +
            "FROM film_likes fl WHERE fl.film_id = f.film_id) WHERE film_id = ?"; //todo check f.film_id

    @Autowired
    public FilmDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long create(FilmRepoDto film) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(CREATE_FILM, new String[]{"film_id"});
            stmt.setString(1, film.getDescription());
            stmt.setInt(2, film.getDuration());
            stmt.setString(3, film.getName());
            stmt.setDate(4, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(5, film.getMpaRatingId());
            return stmt;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public long delete(long id) {
        FilmRepoDto filmToDelete = get(id);
        jdbcTemplate.update(DELETE_FILM, id);
        return id;
    }

    @Override
    public FilmRepoDto get(long id) {
        List<FilmRepoDto> films = jdbcTemplate.query(GET_FILM, (rs, rowNum) -> makeFilmRepoDto(rs), id);
        if (films.isEmpty()) {
            throw new NotFoundException(String.format("Film with id %d isn't exist.", id));
        }
        return films.get(0);
    }

    @Override
    public List<FilmRepoDto> getAll() {
        return jdbcTemplate.query(GET_ALL_FILMS, (rs, rowNum) -> makeFilmRepoDto(rs));
    }

    @Override
    public List<FilmRepoDto> getSome(Collection<Long> ids) {
        String placeholders = String.join(",", Collections.nCopies(ids.size(), "?"));
        String sqlQuery = String.format(GET_SOME_BY_ID, placeholders);
        return jdbcTemplate.query(sqlQuery, ids.toArray(), (rs, rowNum) -> makeFilmRepoDto(rs));
    }

    @Override
    public List<FilmRepoDto> getTop(int count) {
        return jdbcTemplate.query(GET_TOP_FILMS, (rs, rowNum) -> makeFilmRepoDto(rs), count);
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
    public boolean update(FilmRepoDto film) {
        long id = film.getId();
        if (isExist(id)) {
            jdbcTemplate.update(
                    UPDATE_FILM,
                    film.getDescription(),
                    film.getDuration(),
                    film.getLikesCount(),
                    film.getName(),
                    film.getReleaseDate(),
                    film.getMpaRatingId(),
                    film.getId()
            );
            return true;
        } else {
            throw new NotFoundException("Film with id " + id + " isn't exist.");
        }
    }

    @Override
    public boolean updateLikesCount(long id) {
        return jdbcTemplate.update(UPDATE_LIKES_COUNT, id) > 0;
    }

    private FilmRepoDto makeFilmRepoDto(ResultSet rs) throws SQLException {
        long id = rs.getLong("film_id");
        String description = rs.getString("description");
        int duration = rs.getInt("duration");
        int likesCount = rs.getInt("likes_count");
        String name = rs.getString("name");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        int mpaRatingId = rs.getInt("mpa_rating_id");
        return new FilmRepoDto(id, description, duration, likesCount, name, releaseDate, mpaRatingId);
    }
}
