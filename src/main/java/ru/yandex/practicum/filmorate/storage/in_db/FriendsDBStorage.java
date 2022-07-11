package ru.yandex.practicum.filmorate.storage.in_db;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.user.UsersFriends;
import ru.yandex.practicum.filmorate.storage.DataAttributesStorage;

import java.util.Collection;

@Component("friendsDBStorage")
public class FriendsDBStorage implements DataAttributesStorage<UsersFriends> {
    @Override
    public UsersFriends create(UsersFriends dataAttribute) {
        return null;
    }

    @Override
    public UsersFriends delete(long id) {
        return null;
    }

    @Override
    public UsersFriends get(long id) {
        return null;
    }

    @Override
    public Collection<UsersFriends> getAll() {
        return null;
    }

    @Override
    public Collection<UsersFriends> getSome(Collection<Long> ids) {
        return null;
    }

    @Override
    public boolean isExist(long id) {
        return false;
    }

    @Override
    public UsersFriends update(UsersFriends dataAttribute) {
        return null;
    }
}
