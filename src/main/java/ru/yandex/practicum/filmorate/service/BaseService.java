package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.BaseEntry;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public abstract class BaseService <T extends BaseEntry> {
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

    public List<T> getSome(final Collection<Long> ids) {
        return new ArrayList<>(storage.getSome(ids));
    }

    public boolean isEntityExist(final long id) {
        return storage.isExist(id);
    }

    public T update(final T baseEntity) {
        return storage.update(baseEntity);
    }
}
