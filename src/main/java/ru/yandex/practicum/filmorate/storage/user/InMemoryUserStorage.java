package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.IllegalIdException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage{
    private long id;
    private final Map<Long, User> users;

    public InMemoryUserStorage() {
        this.users = new HashMap<>();
        this.id = 1;
    }

    @Override
    public User create(final User user) {
        if (user.getId() != 0) {
            throw new IllegalIdException("Cannot create user with assigned id");
        }
        long currentId = generateId();
        user.setId(currentId);
        users.put(currentId, user);
        return user;
    }

    @Override
    public User delete(final long id) {
        User user = users.remove(id);
        if (user == null) {
            //todo to exc. handler
            throw new NotFoundException("User with id " + id + " isn't exist.");
        }
        return user;
    }

    @Override
    public User get(final long id) {
        User user = users.get(id);
        if (user == null) {
            //todo to exc. handler
            throw new NotFoundException("Film with id " + id + " isn't exist.");
        }
        return user;
    }

    @Override
    public Collection<User> getAll() {
        return users.values();
    }

    @Override
    public User update(final User user) {
        long id = user.getId();
        if (users.containsKey(id)) {
            users.put(id, user);
            return user;
        } else {
            throw new IllegalIdException("Id " + id + " isn't exist");
        }
    }

    private long generateId() {
        return id++;
    }
}
