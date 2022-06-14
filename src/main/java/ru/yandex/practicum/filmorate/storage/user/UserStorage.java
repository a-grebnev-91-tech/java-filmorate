package ru.yandex.practicum.filmorate.storage.user;


import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {
    User create(User user);
    User delete(long id);
    User get(long id);
    Collection<User> getAll();
    User update(User user);
}
