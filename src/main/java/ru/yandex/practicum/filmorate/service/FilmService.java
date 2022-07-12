package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

@Service
public class FilmService {
    private final FilmRatingService ratingService;
    private final FilmStorage filmStorage;
    private final UserService userService;

    @Autowired
    public FilmService(@Qualifier("filmDBStorage") FilmStorage filmStorage,
                       FilmRatingService ratingService,
                       UserService userService) {
        this.filmStorage = filmStorage;
        this.ratingService = ratingService;
        this.userService = userService;
    }

    public void addLike(final long filmId, final long userId) {
        if (isUserExist(userId)) {
            Film film = filmStorage.get(filmId);
            film.addLike();
            ratingService.addLike(filmId, userId);
            filmStorage.update(film);
        } else {
            throw new NotFoundException("User with id " + userId + " isn't exist");
        }
    }

    public Film createFilm(final Film film) {
        return filmStorage.create(film);
    }

    public Film deleteFilm(final long filmId) {
        Film deleted = filmStorage.delete(filmId);
        ratingService.deleteFilm(filmId);
        return deleted;
    }

    public List<Film> getAllFilms() {
        return new ArrayList<>(filmStorage.getAll());
    }

    public Film getFilm(final long filmId) {
        return filmStorage.get(filmId);
    }

    public List<Film> getTopFilms(final int count) {
        return filmStorage.getTop(count);
    }

    public void removeLike(final long filmId, final long userId) {
        if (isUserExist(userId)) {
            Film film = filmStorage.get(filmId);
            if (ratingService.removeLike(filmId, userId)) {
                film.removeLike();
                filmStorage.update(film);
            } else {
                throw new NotFoundException("User with id " + userId + " didn't like the movie with id " + filmId);
            }
        } else {
            throw new NotFoundException("User with id " + userId + " isn't exist");
        }
    }

    public Film updateFilm(final Film film) {
        return filmStorage.update(film);
    }

    private boolean isFilmExist(final long filmId) {
        return filmStorage.isExist(filmId);
    }

    private boolean isUserExist(final long userId) {
        return userService.isUserExist(userId);
    }
}
