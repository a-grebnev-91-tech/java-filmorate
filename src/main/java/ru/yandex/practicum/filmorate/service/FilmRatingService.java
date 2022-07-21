package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.FilmLikesStorage;

@Service
public class FilmRatingService {
    private final FilmLikesStorage storage;

    @Autowired
    public FilmRatingService(@Qualifier("likesDBStorage") FilmLikesStorage storage) {
        this.storage = storage;
    }

    public void addLike(final long filmId, final long userId) {
        storage.addLike(filmId, userId);
    }

    public void deleteFilm(final long filmId) {
        storage.deleteFilmFromRating(filmId);
    }

    public boolean removeLike(final long filmId, final long userId) {
        return storage.removeLike(filmId, userId);
    }

    public int getLikesCount(long filmId) {
        return storage.getLikesCount(filmId);
    }
}
