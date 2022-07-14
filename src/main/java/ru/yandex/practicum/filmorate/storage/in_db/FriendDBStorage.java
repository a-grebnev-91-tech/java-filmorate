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
    private static final String CREATE_FRIENDSHIP = "INSERT INTO friends (user_id, friend_id, confirmed) " +
            "VALUES (?, ?, FALSE)";
    private static final String GET_USERS_FRIENDS = "SELECT friend_id FROM friends WHERE user_id = ? UNION " +
            "SELECT user_id FROM friends WHERE friend_id = ? AND confirmed";
    private static final String GET_MUTUAL_FRIENDS = "SELECT friend_id FROM friends WHERE user_id = ? AND " +
            "friend_id <> ? AND (friend_id IN (SELECT user_id FROM friends WHERE friend_id = ? AND user_id <> ? AND" +
            " confirmed) OR friend_id IN (SELECT friend_id FROM friends WHERE user_id = ? AND friend_id <> ?))";
    private static final String CHECK_FRIENDSHIP_INITIATOR = "SELECT user_id FROM friends WHERE user_id = ? AND " +
            "friend_id = ?";
    private static final String CHECK_FRIENDSHIP_IS_CONFIRMED = "SELECT confirmed FROM friends WHERE user_id = ? AND " +
            "friend_id = ? OR user_id = ? AND friend_id = ?";
    private static final String CONFIRM_FRIENDSHIP = "UPDATE friends SET confirmed = TRUE WHERE user_id = ? AND " +
            "friend_id = ?";
    private static final String REMOVE_FRIENDSHIP_CONFIRMATION = "UPDATE friends SET confirmation = FALSE WHERE " +
            "user_id = ? AND friend_id = ?";
    private static final String REMOVE_FRIENDSHIP = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
    private static final String REMOVE_USER = "DELETE FROM friends WHERE user_id = ? OR friend_id = ?";
    private static final String SWAP_INITIATOR = "UPDATE friends SET user_id = ? AND friend_id = ? WHERE " +
            "user_id = ? AND friend_id = ?";
    private static final String CHECK_UNABLE_TO_CONFIRM_FRIENDSHIP = "SELECT confirmed FROM friends WHERE " +
            "user_id = ? AND friend_id = ?";
    private static final String CHECK_UNABLE_TO_CREATE_FRIENDSHIP = "SELECT confirmed FROM friends " +
            "WHERE (user_id = ? AND friend_id = ?) OR (user_id = ? AND friend_id = ?)";


    @Autowired
    public FriendDBStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriendship(long userId, long anotherUserId) {
        jdbcTemplate.update(CREATE_FRIENDSHIP, userId, anotherUserId);
    }

    @Override
    public List<Long> getFriends(long userId) {
        return jdbcTemplate.queryForList(GET_USERS_FRIENDS, Long.class, userId, userId);
    }

    @Override
    public List<Long> getMutualFriends(long userId, long anotherUserId) {
        return jdbcTemplate.queryForList(
                GET_MUTUAL_FRIENDS,
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
        return jdbcTemplate.queryForList(CHECK_FRIENDSHIP_INITIATOR, initiatorId, friendId).size() > 0;
    }

    @Override
    public boolean isFriendshipExist(long userId, long anotherUserId) {
        return !unableToCreateFriendship(userId, anotherUserId);
    }

    @Override
    public boolean isFriendshipConfirmed(long userId, long anotherUserId) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(
                CHECK_FRIENDSHIP_IS_CONFIRMED,
                userId,
                anotherUserId,
                anotherUserId,
                userId
        );
        if (rowSet.next()) {
            return rowSet.getBoolean("confirmed");
        } else {
            throw new NotFoundException("Friendship between users with id " + userId +
                    " and id " + anotherUserId + " isn't exist");
        }
    }

    @Override
    public void confirmFriendship(long initiatorId, long friendId) {
        jdbcTemplate.update(CONFIRM_FRIENDSHIP, friendId, initiatorId);
    }

    @Override
    public void removeConfirmation(long initiatorId, long friendId) {
        jdbcTemplate.update(REMOVE_FRIENDSHIP_CONFIRMATION, initiatorId, friendId);
    }

    @Override
    public void removeFriendship(long initiatorId, long friendId) {
        jdbcTemplate.update(REMOVE_FRIENDSHIP, initiatorId, friendId);
    }

    @Override
    public void removeUser(long userId) {
        jdbcTemplate.update(REMOVE_USER, userId, userId);
    }

    @Override
    public void swapInitiator(long initiatorId, long friendId) {
        jdbcTemplate.update(SWAP_INITIATOR, friendId, initiatorId, initiatorId, friendId);
    }

    @Override
    public boolean unableToConfirmFriendship(long initiatorId, long friend_id) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(CHECK_UNABLE_TO_CONFIRM_FRIENDSHIP, friend_id, initiatorId);
        if (rowSet.next()) {
            return !rowSet.getBoolean("confirmed");
        } else {
            return false;
        }
    }

    @Override
    public boolean unableToCreateFriendship(long initiatorId, long friendId) {
        List<String> rows = jdbcTemplate.queryForList(
                CHECK_UNABLE_TO_CREATE_FRIENDSHIP,
                String.class, initiatorId,
                friendId,
                friendId,
                initiatorId
        );
        return rows.size() == 0;
    }
}
