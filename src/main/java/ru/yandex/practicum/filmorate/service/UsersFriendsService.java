package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.UsersFriends;
import ru.yandex.practicum.filmorate.storage.DataAttributesStorage;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsersFriendsService {
    private final DataAttributesStorage<UsersFriends> storage;

    @Autowired
    public UsersFriendsService(DataAttributesStorage<UsersFriends> storage) {
        this.storage = storage;
    }

    public void addFriendship(final long userId, final long anotherUserId) {
        addFriend(userId, anotherUserId);
        addFriend(anotherUserId, userId);
    }

    public void addUser(final long userId) {
        storage.create(new UsersFriends(userId));
    }

    public void deleteUser(final long userId) {
        storage.delete(userId);
    }

    public Set<Long> getFriends(final long userId) {
        return storage.get(userId).getFriends();
    }

    public Set<Long> getMutualFriends(final long userId, final long anotherUserId) {
        Collection<Long> userFriends = storage.get(userId).getFriends();
        Collection<Long> anotherUserFriends = storage.get(anotherUserId).getFriends();
        return userFriends.stream().filter(anotherUserFriends::contains).collect(Collectors.toSet());
    }

    public void removeFriendship(final long userId, final long anotherUserId) {
        removeFriend(userId, anotherUserId);
        removeFriend(anotherUserId, userId);
    }

    private void addFriend(long userId, long friendId) {
        UsersFriends entry;
        try {
            entry = storage.get(userId);
            entry.addFriend(friendId);
            storage.update(entry);
        } catch (NotFoundException ex) {
            entry = new UsersFriends(userId);
            entry.addFriend(friendId);
            storage.create(entry);
        }
    }

    private void removeFriend(long userId, long friendId) {
        UsersFriends entry = storage.get(userId);
        entry.removeFriend(friendId);
        storage.update(entry);
    }


}
