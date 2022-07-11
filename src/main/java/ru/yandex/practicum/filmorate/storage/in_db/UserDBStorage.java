package ru.yandex.practicum.filmorate.storage.in_db;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.DataStorage;

import java.util.Collection;

@Component("userDBStorage")
public class UserDBStorage implements DataStorage<User> {
    @Override
    public User create(User baseEntity) {
        return null;
    }

    @Override
    public User delete(long id) {
        return null;
    }

    @Override
    public User get(long id) {
        return null;
    }

    @Override
    public Collection<User> getAll() {
        return null;
    }

    @Override
    public Collection<User> getSome(Collection<Long> ids) {
        return null;
    }

    @Override
    public boolean isExist(long id) {
        return false;
    }

    @Override
    public User update(User baseEntity) {
        return null;
    }
}
