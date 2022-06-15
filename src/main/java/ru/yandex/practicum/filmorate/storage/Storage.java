package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.BaseEntity;

import java.util.Collection;

@Component
public interface Storage <T extends BaseEntity> {
    T create(T baseEntity);
    T delete(long id);
    T get(long id);
    Collection<T> getAll();
    T update(T baseEntity);
}
