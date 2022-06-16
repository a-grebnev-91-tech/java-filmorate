package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.FilmsRatingData;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmsRatingStorage;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmRatingService extends BaseService<FilmsRatingData> {
    public static final Comparator<FilmsRatingData> FILM_RATING_DESC_COMPARATOR =
            Comparator.comparing(FilmsRatingData::rating).reversed();
    private final TreeSet<FilmsRatingData> sortedRatings;

    @Autowired
    public FilmRatingService(InMemoryFilmsRatingStorage storage) {
        super(storage);
        this.sortedRatings = new TreeSet<>(FILM_RATING_DESC_COMPARATOR.thenComparing(FilmsRatingData::getFilmId));
    }

    public void addLike(final long filmId, final long userId) {
        //TODO check and if it's NotFoundException change to try-catch
        FilmsRatingData filmRating = get(filmId);
        if (filmRating == null){
            filmRating = new FilmsRatingData(filmId);
        }
        filmRating.addLike(userId);
        create(filmRating);
        sortedRatings.add(filmRating);
    }

    @Override
    public FilmsRatingData delete(final long id) {
        FilmsRatingData entry = super.delete(id);
        sortedRatings.remove(entry);
        return entry;
    }

    //TODO double check this
    public List<Long> getTop(final int count) {
        List<FilmsRatingData> top;
        if (count > sortedRatings.size()) {
            top = new ArrayList<>(sortedRatings);
        } else {
            top = sortedRatings.stream().limit(count).collect(Collectors.toList());
        }
        return top.stream().map(FilmsRatingData::getFilmId).collect(Collectors.toList());
    }

    public void removeLike(final long filmId, final long userId) {
        FilmsRatingData filmRating = get(filmId);
        sortedRatings.remove(filmRating);
        filmRating.removeLike(userId);
        if (filmRating.rating() != 0) {
            sortedRatings.add(filmRating);
        }
    }

    @Override
    public FilmsRatingData update(FilmsRatingData entry) {
        FilmsRatingData oldEntry = get(entry.getFilmId());
        entry = super.update(entry);
        sortedRatings.remove(oldEntry);
        sortedRatings.add(entry);
        return entry;
    }
}
