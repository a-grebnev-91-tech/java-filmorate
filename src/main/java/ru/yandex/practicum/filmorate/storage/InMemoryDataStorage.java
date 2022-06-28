package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.IllegalIdException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.BaseData;


public abstract class InMemoryDataStorage<T extends BaseData> extends InMemoryStorage<T> implements DataStorage<T> {
    private long id;

    public InMemoryDataStorage() {
        super();
        this.id = 1;
    }

    @Override
    public T create(final T data) {
        if (data.getId() != 0) {
            throw new IllegalIdException("Cannot create " + data + " with assigned id");
        }
        long currentId = generateId();
        data.setId(currentId);
        super.getStorage().put(currentId, data);
        return data;
    }

    @Override
    public T update(final T baseEntity) {
        long id = baseEntity.getId();
        if (super.getStorage().containsKey(id)) {
            super.getStorage().put(id, baseEntity);
            return baseEntity;
        } else {
            throw new NotFoundException("Entry with id  " + id + " isn't exist.");
        }
    }

    private long generateId() {
        return id++;
    }
}