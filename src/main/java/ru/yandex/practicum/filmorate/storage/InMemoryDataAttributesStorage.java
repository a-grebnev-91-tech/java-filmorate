package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.EntryAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.IllegalIdException;
import ru.yandex.practicum.filmorate.model.DataAttributes;

public abstract class InMemoryDataAttributesStorage<T extends DataAttributes>
        extends InMemoryStorage<T>
        implements DataAttributesStorage<T> {

    @Override
    public T create(T dataAttribute) {
        if (isExist(dataAttribute.getDataId())) {
            throw new EntryAlreadyExistException("Cannot create entry because it already exist");
        }
        super.storage.put(dataAttribute.getDataId(), dataAttribute);
        return dataAttribute;
    }

    @Override
    public T update(T baseEntity) {
        long id = baseEntity.getDataId();
        if (storage.containsKey(id)) {
            storage.put(id, baseEntity);
            return baseEntity;
        } else {
            throw new IllegalIdException("Entry with id  " + id + " isn't exist.");
        }
    }
}
