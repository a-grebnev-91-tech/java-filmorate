package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.FilmData;
import ru.yandex.practicum.filmorate.model.FilmsRatingData;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.*;

@Service
public class FilmService extends BaseService<FilmData> {
    private final FilmRatingService ratingService;
    private final UserService userService;

    @Autowired
    public FilmService(Storage<FilmData> storage, FilmRatingService ratingService, UserService userService) {
        super(storage);
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

    @Override
    public FilmData create(final FilmData film) {
        FilmData created = super.create(film);
        ratingService.create(getRatingEntry(film));
        return created;
    }

    @Override
    public FilmData delete(final long id) {
        FilmData deleted = super.delete(id);
        ratingService.delete(deleted.getId());
        return deleted;
    }

    public List<FilmData> getTopFilms(final int count) {
        List<Long> topIds = ratingService.getTop(count);
        return getSome(topIds);
    }

    //todo check
    public void removeLike(final long filmId, final long userId) {
        ratingService.removeLike(filmId, userId);
    }

    private boolean isFilmExist(final long filmId) {
        return isEntityExist(filmId);
    }

    private boolean isUserExist(final long userId) {
        return userService.isEntityExist(userId);
    }

    private FilmsRatingData getRatingEntry(FilmData film) {
        return new FilmsRatingData(film.getId());
    }
}
