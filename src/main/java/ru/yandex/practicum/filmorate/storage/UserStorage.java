package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.user.User;

import java.util.Collection;

public interface UserStorage {
    User create(User baseEntity);
    User delete(long id);
    User get(long id);
    Collection<User> getAll();
    Collection<User> getSome(Collection<Long> ids);
    boolean isExist(long id);
    User update(User baseEntity);
}
