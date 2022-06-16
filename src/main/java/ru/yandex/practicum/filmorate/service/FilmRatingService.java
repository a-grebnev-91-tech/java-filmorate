package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.FilmRatingEntry;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmRatingService extends BaseService<FilmRatingEntry> {
    public static final Comparator<FilmRatingEntry> FILM_RATING_DESC_COMPARATOR =
            Comparator.comparing(FilmRatingEntry::rating).reversed();
    private final TreeSet<FilmRatingEntry> sortedRatings;

    @Autowired
    public FilmRatingService(Storage<FilmRatingEntry> storage) {
        super(storage);
        this.sortedRatings = new TreeSet<>(FILM_RATING_DESC_COMPARATOR.thenComparing(FilmRatingEntry::getFilmId));
    }

    public void addLike(final long filmId, final long userId) {
        FilmRatingEntry filmRating = get(filmId);
        if (filmRating == null){
            filmRating = new FilmRatingEntry(filmId);
        }
        filmRating.addLike(userId);
        create(filmRating);
        sortedRatings.add(filmRating);
    }

    @Override
    public FilmRatingEntry delete(final long id) {
        FilmRatingEntry entry = super.delete(id);
        sortedRatings.remove(entry);
        return entry;
    }

    //TODO double check this
    public List<Long> getTop(final int count) {
        List<FilmRatingEntry> top;
        if (count > sortedRatings.size()) {
            top = new ArrayList<>(sortedRatings);
        } else {
            top = sortedRatings.stream().limit(count).collect(Collectors.toList());
        }
        return top.stream().map(FilmRatingEntry::getFilmId).collect(Collectors.toList());
    }

    public void removeLike(final long filmId, final long userId) {
        FilmRatingEntry filmRating = get(filmId);
        sortedRatings.remove(filmRating);
        filmRating.removeLike(userId);
        if (filmRating.rating() != 0) {
            sortedRatings.add(filmRating);
        }
    }

    @Override
    public FilmRatingEntry update(FilmRatingEntry entry) {
        FilmRatingEntry oldEntry = get(entry.getFilmId());
        entry = super.update(entry);
        sortedRatings.remove(oldEntry);
        sortedRatings.add(entry);
        return entry;
    }
}
