package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.user.User;

import java.util.List;

public interface FriendStorage {
    void addFriendship(long userId, long friendId);
    void removeFriendship(long userId, long friendId);
    void removeUser(long userId);
    List<User> getFriends(long userId);
    List<User> getMutualFriends(long userId, long friendId);
}
