package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

public class LikeRankedFilm {
    private final Map<Integer, Map<Long, Set<Long>>> films;

    public LikeRankedFilm(Collection<Film> films) {
        this.films = new HashMap<>();
        for (Film film : films) {
            add(film);
        }
    }

    public void add(final Film film) {
        long id = film.getId();
        int rating = film.rating();
        Set<Long> likes = Set.copyOf(film.getLikes());
        if (films.containsKey(rating)) {
            films.get(rating).put(id, likes);
        } else {
            Map<Long, Set<Long>> oneFilm = new HashMap<>();
            oneFilm.put(id, likes);
            this.films.put(rating, oneFilm);
        }
    }

    //TODO double check this
    public List<Long> getTop(final int count) {
        List<Long> result = new ArrayList<>();
        List<Integer> ratings = getAllRatings();
        for (int i = 0; result.size() < count; i++) {
            Map<Long, Set<Long>> filmsWithSameRating = films.get(ratings.get(i));
            for (Long id : filmsWithSameRating.keySet()) {
                result.add(id);
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

    public void update(final Film newVersion, final int oldRating) {
        Map<Long, Set<Long>> filmsWithSameRating = films.get(oldRating);
        filmsWithSameRating.remove(newVersion.getId());
        add(newVersion);
    }

    private List<Integer> getAllRatings() {
        List<Integer> ratings = films.keySet().stream().sorted().collect(Collectors.toList());
        Collections.reverse(ratings);
        return ratings;
    }
}
