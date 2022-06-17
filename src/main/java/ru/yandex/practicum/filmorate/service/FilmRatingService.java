package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.film.FilmsRating;
import ru.yandex.practicum.filmorate.storage.DataAttributesStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmRatingService {
    public static final Comparator<FilmsRating> FILM_RATING_DESC_COMPARATOR =
            Comparator.comparing(FilmsRating::rating).reversed();
    private final TreeSet<FilmsRating> sortedRatings;
    private final DataAttributesStorage<FilmsRating> storage;

    @Autowired
    public FilmRatingService(DataAttributesStorage<FilmsRating> storage) {
        this.storage = storage;
        this.sortedRatings = new TreeSet<>(FILM_RATING_DESC_COMPARATOR.thenComparing(FilmsRating::getFilmId));
    }

    public void addLike(final long filmId, final long userId) {
        //TODO check and if it's NotFoundException change to try-catch
        FilmsRating entry = storage.get(filmId);
        if (entry == null) {
            entry = new FilmsRating(filmId);
            entry.addLike(userId);
            storage.create(entry);
        } else {
            storage.update(entry);
        }
        sortedRatings.add(entry);
    }

    public void addFilm(final long filmId) {
        FilmsRating entry = storage.create(new FilmsRating(filmId));
        sortedRatings.add(entry);
    }

    public void deleteFilm(final long filmId) {
        FilmsRating deletedEntry = storage.delete(filmId);
        sortedRatings.remove(deletedEntry);
    }

    //TODO double check this
    public List<Long> getTop(final int count) {
        List<FilmsRating> top;
        if (count > sortedRatings.size()) {
            top = new ArrayList<>(sortedRatings);
        } else {
            top = sortedRatings.stream().limit(count).collect(Collectors.toList());
        }
        return top.stream().map(FilmsRating::getFilmId).collect(Collectors.toList());
    }

    public void removeLike(final long filmId, final long userId) {
        FilmsRating entry = storage.get(filmId);
        sortedRatings.remove(entry);
        entry.removeLike(userId);
        sortedRatings.add(entry);
        storage.update(entry);
    }
//TODO delete
//    private FilmsRating update(FilmsRating entry) {
//        FilmsRating oldEntry = storage.get(entry.getFilmId());
//        entry = storage.update(entry);
//        sortedRatings.remove(oldEntry);
//        sortedRatings.add(entry);
//        return entry;
//    }
}
