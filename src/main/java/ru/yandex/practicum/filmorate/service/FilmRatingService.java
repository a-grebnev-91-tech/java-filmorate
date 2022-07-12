package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.FilmsRating;
import ru.yandex.practicum.filmorate.storage.DataAttributesStorage;

@Service
public class FilmRatingService {
    private final DataAttributesStorage<FilmsRating> storage;

    @Autowired
    public FilmRatingService(@Qualifier("likesDBStorage") DataAttributesStorage<FilmsRating> storage) {
        this.storage = storage;
    }

    public void addLike(final long filmId, final long userId) {
        FilmsRating entry;
        try {
            entry = storage.get(filmId);
            entry.addLike(userId);
            storage.update(entry);
        } catch (NotFoundException ex) {
            entry = new FilmsRating(filmId);
            entry.addLike(userId);
            storage.create(entry);
        }
    }

    public void deleteFilm(final long filmId) {
        FilmsRating deletedEntry = storage.delete(filmId);
    }

    public void removeLike(final long filmId, final long userId) {
        FilmsRating entry = storage.get(filmId);
        try {
            entry.removeLike(userId);
        } catch (NotFoundException ex) {
            throw ex;
        } finally {
            storage.update(entry);
        }
    }
}
