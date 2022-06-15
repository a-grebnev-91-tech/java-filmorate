package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.BaseEntity;

import java.util.Collection;

public interface Storage <T extends BaseEntity> {
    T create(T baseEntity);
    T delete(long id);
    T get(long id);
    Collection<T> getAll();
    T update(T baseEntity);
}
