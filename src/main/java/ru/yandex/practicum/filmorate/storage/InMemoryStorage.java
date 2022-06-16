package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.IllegalIdException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.BaseEntity;

import java.util.*;

@Component
public class InMemoryStorage<T extends BaseEntity> implements Storage<T> {
    private long id;
    private final Map<Long, T> vault;

    public InMemoryStorage() {
        this.vault = new HashMap<>();
        this.id = 1;
    }

    @Override
    public T create(final T baseEntity) {
        if (baseEntity.getId() != 0) {
            throw new IllegalIdException("Cannot create " + baseEntity + " with assigned id");
        }
        long currentId = generateId();
        baseEntity.setId(currentId);
        vault.put(currentId, baseEntity);
        return baseEntity;
    }

    @Override
    public T delete(final long id) {
        T baseEntity = vault.remove(id);
        if (baseEntity == null) {
            throw new NotFoundException("Entity with id " + id + " isn't exist.");
        }
        return baseEntity;
    }

    @Override
    public T get(final long id) {
        T baseEntity = vault.get(id);
        if (baseEntity == null) {
            throw new NotFoundException("Entity with id " + id + " isn't exist.");
        }
        return baseEntity;
    }

    @Override
    public Collection<T> getAll() {
        return vault.values();
    }

    @Override
    public Collection<T> getSome(final Collection<Long> ids) {
        List<T> entities = new ArrayList<>();
        for (Long id : ids) {
            entities.add(get(id));
        }
        return entities;
    }

    @Override
    public boolean isExist(final long id) {
        return vault.containsKey(id);
    }

    @Override
    public T update(final T baseEntity) {
        long id = baseEntity.getId();
        if (vault.containsKey(id)) {
            vault.put(id, baseEntity);
            return baseEntity;
        } else {
            throw new IllegalIdException("Entity with id  " + id + " isn't exist.");
        }
    }

    private long generateId() {
        return id++;
    }
}
