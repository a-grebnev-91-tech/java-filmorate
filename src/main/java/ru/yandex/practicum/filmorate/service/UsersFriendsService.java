package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;

import java.util.List;

@Service
public class UsersFriendsService {
    private final FriendStorage storage;

    @Autowired
    public UsersFriendsService(@Qualifier("friendsDBStorage") FriendStorage storage) {
        this.storage = storage;
    }

    public void addFriendship(final long userId, final long anotherUserId) {
        storage.addFriendship(userId, anotherUserId);
    }

    public void removeUser(final long userId) {
        storage.removeUser(userId);
    }

    public List<User> getFriends(final long userId) {
        return storage.getFriends(userId);
    }

    public List<User> getMutualFriends(final long userId, final long anotherUserId) {
        return storage.getMutualFriends(userId, anotherUserId);
    }

    public void removeFriendship(final long userId, final long anotherUserId) {
        storage.removeFriendship(userId, anotherUserId);
    }
}
