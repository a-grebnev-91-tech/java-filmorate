package ru.yandex.practicum.filmorate.storage.in_db;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.FriendStorage;

import java.util.List;

@Component("friendsDBStorage")
public class FriendsDBStorage implements FriendStorage {
    @Override
    public void addFriendship(long userId, long friendId) {

    }

    @Override
    public List<User> getFriends(long userId) {
        return null;
    }

    @Override
    public List<User> getMutualFriends(long userId, long friendId) {
        return null;
    }

    @Override
    public void removeFriendship(long userId, long friendId) {

    }

    @Override
    public void removeUser(long userId) {

    }
}
