package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.DataStorage;

import java.util.*;

@Service
public class FilmService {
    private final FilmRatingService ratingService;
    private final DataStorage<Film> filmsStorage;
    private final UserService userService;

    @Autowired
    public FilmService(DataStorage<Film> dataStorage, FilmRatingService ratingService, UserService userService) {
        this.filmsStorage = dataStorage;
        this.ratingService = ratingService;
        this.userService = userService;
    }

    //TODO check
    public void addLike(final long filmId, final long userId) {
        if (isUserExist(userId) && isFilmExist(filmId)) {
            ratingService.addLike(filmId, userId);
        } else if (isUserExist(userId)) {
            throw new NotFoundException("Film with id " + filmId + " isn't exist");
        } else {
            throw new NotFoundException("User with id " + userId + " isn't exist");
        }
    }

    public Film createFilm(final Film film) {
        Film created = filmsStorage.create(film);
        ratingService.addFilm(film.getId());
        return created;
    }

    public Film deleteFilm(final long filmId) {
        Film deleted = filmsStorage.delete(filmId);
        ratingService.deleteFilm(filmId);
        return deleted;
    }

    public List<Film> getAllFilms() {
        return new ArrayList<>(filmsStorage.getAll());
    }

    public Film getFilm(final long filmId) {
        return filmsStorage.get(filmId);
    }

    public List<Film> getTopFilms(final int count) {
        List<Long> topIds = ratingService.getTop(count);
        return new ArrayList<>(filmsStorage.getSome(topIds));
    }

    //todo check
    public void removeLike(final long filmId, final long userId) {
        ratingService.removeLike(filmId, userId);
    }

    public Film updateFilm(final Film film) {
        return filmsStorage.update(film);
    }

    private boolean isFilmExist(final long filmId) {
        return filmsStorage.isExist(filmId);
    }

    private boolean isUserExist(final long userId) {
        return userService.isUserExist(userId);
    }
}
