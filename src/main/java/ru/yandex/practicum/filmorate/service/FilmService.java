package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.LikeRankedFilmStorage;

import java.util.*;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage storage;
    private final LikeRankedFilmStorage likeRankedFilms;

    @Autowired
    public FilmService(FilmStorage storage) {
        this.storage = storage;
        this.likeRankedFilms = new LikeRankedFilmStorage(storage.getAll());
    }

    public Film create(final Film film) {
        Film created = storage.create(film);
        log.info("Add film {}", film);
        likeRankedFilms.add(created);
        return created;
    }

    public Film delete(final long id) {
        Film deleted = storage.delete(id);
        log.info("Film {} has been removed", deleted);
        likeRankedFilms.remove(deleted);
        return deleted;
    }

    public Film get(final long id) {
        return storage.get(id);
    }

    public List<Film> getTopFilms(final int count) {
        log.info("Get top {} movies", count);
        return likeRankedFilms.getTopFilms(count);
    }


    public Film update(final Film film) {
        Film oldVersion = storage.get(film.getId());
        Film newVersion = storage.update(film);
        log.info("Update film {}", film);
        likeRankedFilms.update(oldVersion, newVersion);
        return newVersion;
    }
}
