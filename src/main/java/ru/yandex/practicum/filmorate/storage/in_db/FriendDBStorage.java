package ru.yandex.practicum.filmorate.storage.in_db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.storage.FriendStorage;

import java.util.List;

@Component("friendsDBStorage")
public class FriendDBStorage implements FriendStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriendship(long userId, long anotherUserId) {
        String sqlQuery = "INSERT INTO friends (user_id, friend_id, confirmed) VALUES (?, ?, FALSE)";
        jdbcTemplate.update(sqlQuery, userId, anotherUserId);
    }

    @Override
    public List<Long> getFriends(long userId) {
        String sqlQuery = "SELECT friend_id FROM friends WHERE user_id = ? " +
                "UNION " +
                "SELECT user_id FROM friends WHERE friend_id = ? AND confirmed";
        return jdbcTemplate.queryForList(sqlQuery, Long.class, userId, userId);
    }

    @Override
    public List<Long> getMutualFriends(long userId, long anotherUserId) {
        String sqlQuery = "SELECT friend_id FROM friends WHERE user_id = ? AND friend_id <> ? AND (friend_id IN " +
                "(SELECT user_id FROM friends WHERE friend_id = ? AND user_id <> ? AND confirmed) OR " +
                "friend_id IN (SELECT friend_id FROM friends WHERE user_id = ? AND friend_id <> ?))";
        return jdbcTemplate.queryForList(
                sqlQuery,
                Long.class,
                userId,
                anotherUserId,
                anotherUserId,
                userId,
                anotherUserId,
                userId
        );
    }

    @Override
    public boolean isFriendshipInitiator(long initiatorId, long friendId) {
        String sqlQuery = "SELECT user_id FROM friends WHERE user_id = ? AND friend_id = ?";
        return jdbcTemplate.queryForList(sqlQuery, initiatorId, friendId).size() > 0;
    }

    @Override
    public boolean isFriendshipExist(long userId, long anotherUserId) {
        return !unableToCreateFriendship(userId, anotherUserId);
    }

    @Override
    public boolean isFriendshipConfirmed(long userId, long anotherUserId) {
        String sqlQuery =
                "SELECT confirmed FROM friends WHERE user_id = ? AND friend_id = ? OR user_id = ? AND friend_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlQuery, userId, anotherUserId, anotherUserId, userId);
        if (rowSet.next()) {
            return rowSet.getBoolean("confirmed");
        } else {
            throw new NotFoundException("Friendship between users with id " + userId +
                    " and id " + anotherUserId + " isn't exist");
        }
    }

    @Override
    public void confirmFriendship(long initiatorId, long friendId) {
        String sqlQuery = "UPDATE friends SET confirmed = TRUE WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sqlQuery, friendId, initiatorId);
    }

    @Override
    public void removeConfirmation(long initiatorId, long friendId) {
        String sqlQuery = "UPDATE friends SET confirmation = FALSE WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sqlQuery, initiatorId, friendId);
    }

    @Override
    public void removeFriendship(long initiatorId, long friendId) {
        String sqlQuery = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sqlQuery, initiatorId,friendId);
    }

    @Override
    public void removeUser(long userId) {
        String sqlQuery = "DELETE FROM friends WHERE user_id = ? OR friend_id = ?";
        jdbcTemplate.update(sqlQuery, userId, userId);
    }

    @Override
    public void swapInitiator(long initiatorId, long friendId) {
        String sqlQuery = "UPDATE friends SET user_id = ? AND friend_id = ? WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sqlQuery, friendId, initiatorId, initiatorId, friendId);
    }

    @Override
    public boolean unableToConfirmFriendship(long initiatorId, long friend_id) {
        String sqlQuery = "SELECT confirmed FROM friends WHERE user_id = ? AND friend_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlQuery, friend_id, initiatorId);
        if (rowSet.next()) {
            return !rowSet.getBoolean("confirmed");
        } else {
            return false;
        }
    }

    @Override
    public boolean unableToCreateFriendship(long initiatorId, long friendId) {
        String sqlQuery = "SELECT confirmed FROM friends " +
                "WHERE (user_id = ? AND friend_id = ?) OR (user_id = ? AND friend_id = ?)";
        List<String> rows =
                jdbcTemplate.queryForList(sqlQuery, String.class, initiatorId, friendId, friendId, initiatorId);
        return rows.size() == 0;
    }
}
