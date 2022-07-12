package ru.yandex.practicum.filmorate.storage.in_db;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.FilmsRating;
import ru.yandex.practicum.filmorate.storage.DataAttributesStorage;

import java.util.Collection;

@Component("likesDBStorage")
public class LikesDBStorage implements DataAttributesStorage<FilmsRating> {
    @Override
    public FilmsRating create(FilmsRating filmsRating) {
        return null;
    }

    @Override
    public FilmsRating delete(long id) {
        return null;
    }

    @Override
    public FilmsRating get(long id) {
        return null;
    }

    @Override
    public Collection<FilmsRating> getAll() {
        return null;
    }

    @Override
    public Collection<FilmsRating> getSome(Collection<Long> ids) {
        return null;
    }

    @Override
    public boolean isExist(long id) {
        return false;
    }

    @Override
    public FilmsRating update(FilmsRating dataAttribute) {
        return null;
    }
}
