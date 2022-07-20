package ru.yandex.practicum.filmorate.storage;

public interface FilmRatingStorage {
    void addLike(long filmId, long userId);

    void deleteFilmFromRating(long filmId);

    boolean removeLike(long filmId, long userId);
}
