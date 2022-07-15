package ru.yandex.practicum.filmorate.storage;

import java.util.List;

public interface FriendStorage {
    void addFriendship(long userId, long anotherUserId);

    List<Long> getFriends(long userId);

    List<Long> getMutualFriends(long userId, long anotherUserId);

    boolean isFriendshipInitiator(long initiatorId, long friendId);

    boolean isFriendshipExist(long userId, long anotherUserId);

    boolean isFriendshipConfirmed(long userId, long anotherUserId);

    void confirmFriendship(long userId, long friendId);

    void removeConfirmation(long initiatorId, long friendId);

    void removeFriendship(long initiatorId, long friendId);

    void removeUser(long userId);

    void swapInitiator(long initiatorId, long friendId);

    boolean unableToConfirmFriendship(long initiatorId, long friend_id);

    boolean unableToCreateFriendship(long initiatorId, long friendId);
}
