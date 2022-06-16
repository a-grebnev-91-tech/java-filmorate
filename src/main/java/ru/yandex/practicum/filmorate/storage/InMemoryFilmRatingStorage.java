package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.EntryAlreadyExistException;
import ru.yandex.practicum.filmorate.model.FilmRatingEntry;

public class InMemoryFilmRatingStorage<T extends FilmRatingEntry> extends InMemoryStorage<T> {

    @Override
    public T create(T entry) {
        if (isExist(entry.getFilmId())) {
            throw new EntryAlreadyExistException("Cannot create film rating entry because it already exist");
        }
        super.storage.put(entry.getFilmId(), entry);
        return entry;
    }
}
