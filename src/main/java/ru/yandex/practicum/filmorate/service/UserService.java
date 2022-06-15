package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;

@Service
public class UserService extends BaseService<User> {

    @Autowired
    public UserService(Storage<User> storage) {
        super(storage);
    }

    public boolean addFriend(final long firstId, final long secondId) {
        throw new RuntimeException("Not implemented");
    }

    public List<User> getFriends(final long id) {
        return null;
    }

    public List<User> getMutualFriends(final long firstId, final long secondId) {
        throw new RuntimeException("Not impl");
    }
}
