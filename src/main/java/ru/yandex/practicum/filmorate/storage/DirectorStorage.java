package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.film.FilmDirector;

import java.util.List;

public interface DirectorStorage {
    long create(FilmDirector director);

    FilmDirector delete(long id);

    FilmDirector get(long id);

    List<FilmDirector> getAll();

    boolean update(FilmDirector director);
}
