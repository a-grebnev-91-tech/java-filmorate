package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Service
public class UserService {
    private final UserStorage storage;

    @Autowired
    public UserService(UserStorage storage) {
        this.storage = storage;
    }

    public boolean addFriend(final long firstId, final long secondId) {

    }

    public User create(final User user) {
        return storage.create(user);
    }

    public User delete(final long id) {
        return storage.delete(id);
    }

    public User get(final long id) {
        return storage.get(id);
    }

    public List<User> getFriends(final long id) {
        return null;
    }

    public List<User> getMutualFriends(final long firstId, final long secondId) {

    }

    public User update(final User user) {
        return storage.update(user);
    }

}
