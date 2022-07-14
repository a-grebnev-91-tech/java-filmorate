package ru.yandex.practicum.filmorate.storage.in_db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.DataStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.sql.Date;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component("userDBStorage")
public class UserDBStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;
    private static final String CREATE_USER = "INSERT INTO users (birthday, email, login, name) VALUES (?, ?, ?, ?)";
    private static final String DELETE_USER = "DELETE FROM users WHERE user_id = ?";
    private static final String GET_USER_BY_ID = "SELECT * FROM users WHERE user_id = ?";
    private static final String GET_ALL_USERS = "SELECT * FROM users";
    private static final String GET_SOME_USERS_BY_ID = "SELECT * FROM users WHERE user_id IN (%s)";
    private static final String UPDATE_USER = "UPDATE users SET birthday = ?, email = ?, login = ?, name = ? WHERE " +
            "user_id = ?";

    @Autowired
    public UserDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(CREATE_USER, new String[]{"user_id"});
            stmt.setDate(1, Date.valueOf(user.getBirthday()));
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getLogin());
            stmt.setString(4, user.getName());
            return stmt;
        }, keyHolder);
        long generatedId = keyHolder.getKey().longValue();
        user.setId(generatedId);
        return user;
    }

    @Override
    public User delete(long id) {
        User userToDelete = get(id);
        jdbcTemplate.update(DELETE_USER, id);
        return userToDelete;
    }

    @Override
    public User get(long id) {
        List<User> users = jdbcTemplate.query(GET_USER_BY_ID, (rs, rowNum) -> makeUser(rs), id);
        if (users.size() < 1) {
            throw new NotFoundException("User with id " + id + " isn't exist.");
        }
        return users.get(0);
    }

    @Override
    public Collection<User> getAll() {
        return jdbcTemplate.query(GET_ALL_USERS, (rs, rowNum) -> makeUser(rs));
    }

    @Override
    public Collection<User> getSome(Collection<Long> ids) {
        String placeholders = String.join(",", Collections.nCopies(ids.size(), "?"));
        String sqlQuery = String.format(GET_SOME_USERS_BY_ID, placeholders);
        return jdbcTemplate.query(sqlQuery,ids.toArray(), (rs, rowNum) -> makeUser(rs));
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
    public User update(User user) {
        long id = user.getId();
        if (isExist(id)) {
            jdbcTemplate.update(UPDATE_USER, user.getBirthday(), user.getEmail(), user.getLogin(), user.getName(), id);
            return user;
        } else {
            throw new NotFoundException("User with id " + id + " isn't exist.");
        }
    }

    private User makeUser(ResultSet rs) throws SQLException {
        long id = rs.getLong("user_id");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        String email = rs.getString("email");
        String login = rs.getString("login");
        String name = rs.getString("name");
        return new User(id, email, login, name, birthday);
    }
}
