package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.NotFoundException;

import java.util.*;

public abstract class InMemoryStorage<T> {
    final Map<Long, T> storage;

    public InMemoryStorage() {
        this.storage = new HashMap<>();
    }

    public abstract T create(final T data);

    public T delete(final long id) {
        T baseEntity = storage.remove(id);
        if (baseEntity == null) {
            throw new NotFoundException("Entry with id " + id + " isn't exist.");
        }
        return baseEntity;
    }

    public T get(final long id) {
        T baseEntity = storage.get(id);
        if (baseEntity == null) {
            throw new NotFoundException("Entry with id " + id + " isn't exist.");
        }
        return baseEntity;
    }

    public Collection<T> getAll() {
        return storage.values();
    }

    public Collection<T> getSome(final Collection<Long> ids) {
        List<T> entities = new ArrayList<>();
        for (Long id : ids) {
            entities.add(get(id));
        }
        return entities;
    }

    public boolean isExist(final long id) {
        return storage.containsKey(id);
    }

    public abstract T update(final T baseEntity);
}
