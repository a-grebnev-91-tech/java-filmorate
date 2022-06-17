package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.DataAttributes;

import java.util.Collection;

//TODO check raw use generic
//вопрос к ревьюеру: идея ругается на использование чистого DataAttributes без спецификатора типа.
// Нормально ли такое использование?
public interface DataAttributesStorage<T extends DataAttributes> {
    T create(T dataAttribute);
    T delete(long id);
    T get(long id);
    Collection<T> getAll();
    Collection<T> getSome(Collection<Long> ids);
    boolean isExist(long id);
    T update(T dataAttribute);
}
