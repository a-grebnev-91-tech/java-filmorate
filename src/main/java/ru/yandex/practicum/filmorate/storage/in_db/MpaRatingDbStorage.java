package ru.yandex.practicum.filmorate.storage.in_db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.MpaRating;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;

import javax.sql.RowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MpaRatingDbStorage implements MpaRatingStorage {
    private final JdbcTemplate jdbcTemplate;
    private static final String GET_BY_ID = "SELECT * FROM mpa_rating WHERE mpa_rating_id = ?";
    private static final String GET_ALL = "SELECT * FROM mpa_rating";

    @Autowired
    public MpaRatingDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MpaRating get(int id) {
        List<MpaRating> ratings = jdbcTemplate.query(GET_BY_ID, (rs, rowNum) -> makeMpaRating(rs), id);
        if (ratings.isEmpty()) {
            throw new NotFoundException("Mpa rating with id " + id + " isn't exist");
        }
        return ratings.get(0);
    }

    @Override
    public List<MpaRating> getAll() {
        return jdbcTemplate.query(GET_ALL, (rs, rowNum) -> makeMpaRating(rs));
    }

    private MpaRating makeMpaRating(ResultSet rs) throws SQLException {
        return new MpaRating(rs.getInt("mpa_rating_id"), rs.getString("name"));
    }
}
