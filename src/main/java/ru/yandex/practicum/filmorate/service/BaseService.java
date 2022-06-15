package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.BaseEntity;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.Collection;

@Service
public abstract class BaseService <T extends BaseEntity> {
    private final Storage<T> storage;

    @Autowired
    public BaseService(Storage<T> storage) {
        this.storage = storage;
    }

    public T create(final T baseEntity) {
        return storage.create(baseEntity);
    }

    public T delete(final long id) {
        return storage.delete(id);
    }

    public T get(final long id) {
        return storage.get(id);
    }

    public Collection<T> getAll() {
        return storage.getAll();
    }

    public T update(final T baseEntity) {
        return storage.update(baseEntity);
    }
}
