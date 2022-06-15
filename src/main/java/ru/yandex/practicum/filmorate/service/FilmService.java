package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.Storage;
import ru.yandex.practicum.filmorate.storage.film.LikeRankedFilmStorage;

import java.util.*;

@Service
public class FilmService extends BaseService<Film> {
    private final LikeRankedFilmStorage likeRankedFilms;

    @Autowired
    public FilmService(Storage<Film> storage) {
        super(storage);
        this.likeRankedFilms = new LikeRankedFilmStorage(storage.getAll());
    }

    @Override
    public Film create(final Film film) {
        Film created = super.create(film);
        likeRankedFilms.add(created);
        return created;
    }

    @Override
    public Film delete(final long id) {
        Film deleted = super.delete(id);
        likeRankedFilms.remove(deleted);
        return deleted;
    }

    public List<Film> getTopFilms(final int count) {
        return likeRankedFilms.getTopFilms(count);
    }


    @Override
    public Film update(final Film film) {
        Film oldVersion = super.get(film.getId());
        Film newVersion = super.update(film);
        likeRankedFilms.update(oldVersion, newVersion);
        return newVersion;
    }
}
