package ru.yandex.practicum.filmorate.storage.in_db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.FilmRatingStorage;

@Component("likesDBStorage")
public class LikesDBStorage implements FilmRatingStorage {
    private final JdbcTemplate jdbcTemplate;
    private static final String CREATE_LIKE = "INSERT INTO film_likes (film_id, user_id) VALUES (?, ?)";
    private static final String DELETE_FILM_FROM_RATING = "DELETE FROM film_likes WHERE film_id = ?";
    private static final String REMOVE_LIKE = "DELETE FROM film_likes WHERE film_id = ? AND user_id = ?";

    @Autowired
    public LikesDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(long filmId, long userId) {
        jdbcTemplate.update(CREATE_LIKE, filmId, userId);
    }

    @Override
    public void deleteFilmFromRating(long filmId) {
        jdbcTemplate.update(DELETE_FILM_FROM_RATING, filmId);
    }

    @Override
    public boolean removeLike(long filmId, long userId) {
        return jdbcTemplate.update(REMOVE_LIKE, filmId, userId) > 0;
    }
}
