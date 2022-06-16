package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.IllegalIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.*;

@Service
public class FilmService extends BaseService<Film> {
    private final FilmRatingService likeRankedFilms;
    private final UserService userService;

    @Autowired
    public FilmService(Storage<Film> storage, UserService userService) {
        super(storage);
        this.userService = userService;
        this.likeRankedFilms = new FilmRatingService(storage.getAll());
    }

    //TODO check
    public void addLike(final long filmId, final long userId) {
        changeRating(filmId, userId, true);
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
        List<Long> topIds = likeRankedFilms.getTop(count);
        return getSome(topIds);
    }

    //todo check
    public void removeLike(final long filmId, final long userId) {
        changeRating(filmId, userId, false);
    }

    @Override
    public Film update(final Film film) {
        Film oldVersion = super.get(film.getId());
        int oldRating = oldVersion.rating();
        Film newVersion = super.update(film);
        likeRankedFilms.update(newVersion, oldRating);
        return newVersion;
    }

    private void changeRating(final long filmId, final long userId, final boolean isLike) {
        if (isUserExist(userId)) {
            Film film = get(filmId);
            int oldRating = film.rating();
            if (isLike) {
                film.addLike(userId);
            } else {
                film.removeLike(userId);
            }
            super.update(film);
            likeRankedFilms.update(film, oldRating);
        } else {
            throw new IllegalIdException("User with id " + userId + " isn't exist");
        }
    }
    private boolean isUserExist(final long userId) {
        return userService.isEntityExist(userId);
    }
}
