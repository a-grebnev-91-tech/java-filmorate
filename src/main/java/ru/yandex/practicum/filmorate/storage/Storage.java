package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.BaseEntry;

import java.util.Collection;

@Component
public interface Storage<T extends BaseEntry> {
    T create(T baseEntity);
    T delete(long id);
    T get(long id);
    Collection<T> getAll();
    Collection<T> getSome(Collection<Long> ids);
    boolean isExist(long id);
    T update(T baseEntity);
}
