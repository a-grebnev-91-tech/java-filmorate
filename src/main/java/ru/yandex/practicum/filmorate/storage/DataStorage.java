package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Data;

import java.util.Collection;

@Component
public interface DataStorage<T extends Data> {
    T create(T baseEntity);
    T delete(long id);
    T get(long id);
    Collection<T> getAll();
    Collection<T> getSome(Collection<Long> ids);
    boolean isExist(long id);
    T update(T baseEntity);
}
