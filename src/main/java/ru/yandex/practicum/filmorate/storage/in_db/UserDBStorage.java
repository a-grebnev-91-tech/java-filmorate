package ru.yandex.practicum.filmorate.storage.in_db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.DataStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component("userDBStorage")
public class UserDBStorage implements DataStorage<User> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDBStorage(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User baseEntity) {
        return null;
    }

    @Override
    public User delete(long id) {
        return null;
    }

    @Override
    public User get(long id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        List<User> users = jdbcTemplate.query(sql, (rs, rowNum) -> makeUser(rs), id);
        if (users.size() < 1) {
            throw new NotFoundException("User with id " + id + " isn't exist.");
        }
        return users.get(0);
    }

    @Override
    public Collection<User> getAll() {
        return null;
    }

    @Override
    public Collection<User> getSome(Collection<Long> ids) {
        return null;
    }

    @Override
    public boolean isExist(long id) {
        return false;
    }

    @Override
    public User update(User baseEntity) {
        return null;
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
