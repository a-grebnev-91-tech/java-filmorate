package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.BaseData;

import java.util.Collection;

public interface DataStorage<T extends BaseData> {
    T create(T baseEntity);
    T delete(long id);
    T get(long id);
    Collection<T> getAll();
    Collection<T> getSome(Collection<Long> ids);
    boolean isExist(long id);
    T update(T baseEntity);
}
