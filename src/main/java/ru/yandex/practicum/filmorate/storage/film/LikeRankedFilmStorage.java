package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

public class LikeRankedFilmStorage {
    private final Map<Integer, Map<Long, Film>> films;

    public LikeRankedFilmStorage(Collection<Film> films) {
        this.films = new HashMap<>();
        for (Film film : films) {
            add(film);
        }
    }

    public void add(final Film film) {
        int filmRating = film.rating();
        if (films.containsKey(filmRating)) {
            films.get(filmRating).put(film.getId(), film);
        } else {
            Map<Long, Film> films = new HashMap<>();
            films.put(film.getId(), film);
            this.films.put(filmRating, films);
        }
    }

    //TODO double check this
    public List<Film> getTopFilms(final int count) {
        List<Film> result = new ArrayList<>();
        List<Integer> ratings = getAllRatings();
        for (int i = 0; result.size() < count; i++) {
            Map<Long, Film> filmsWithSameRating = films.get(ratings.get(i));
            for (Map.Entry<Long, Film> film : filmsWithSameRating.entrySet()) {
                result.add(film.getValue());
                if (result.size() == count)
                    break;
            }
        }
        return result;
    }

    public void remove(final Film filmToRemove) {
        int rating = filmToRemove.rating();
        long id = filmToRemove.getId();
        films.get(rating).remove(id);
        if (films.get(rating).size() == 0) {
            films.remove(rating);
        }
    }

    public void update(final Film oldVersion, final Film newVersion) {
        if (oldVersion == null) {
            add(newVersion);
        } else {
            remove(oldVersion);
            add(newVersion);
        }
    }

    private List<Integer> getAllRatings() {
        List<Integer> ratings = films.keySet().stream().sorted().collect(Collectors.toList());
        Collections.reverse(ratings);
        return ratings;
    }
}
