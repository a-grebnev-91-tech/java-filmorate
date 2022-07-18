package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {
    Film create(Film baseEntity);

    Film delete(long id);

    Film get(long id);

    Collection<Film> getAll();

    List<Film> getTop(int count);

    Collection<Film> getSome(Collection<Long> ids);

    boolean isExist(long id);

    Film update(Film baseEntity);

}
