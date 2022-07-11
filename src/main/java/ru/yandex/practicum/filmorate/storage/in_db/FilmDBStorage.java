package ru.yandex.practicum.filmorate.storage.in_db;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.DataStorage;

import java.util.Collection;

@Component("filmDBStorage")
public class FilmDBStorage implements DataStorage<Film> {
    @Override
    public Film create(Film baseEntity) {
        return null;
    }

    @Override
    public Film delete(long id) {
        return null;
    }

    @Override
    public Film get(long id) {
        return null;
    }

    @Override
    public Collection<Film> getAll() {
        return null;
    }

    @Override
    public Collection<Film> getSome(Collection<Long> ids) {
        return null;
    }

    @Override
    public boolean isExist(long id) {
        return false;
    }

    @Override
    public Film update(Film baseEntity) {
        return null;
    }
}
