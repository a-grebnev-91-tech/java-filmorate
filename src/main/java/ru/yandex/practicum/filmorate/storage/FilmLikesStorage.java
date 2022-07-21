package ru.yandex.practicum.filmorate.storage;

public interface FilmLikesStorage {
    void addLike(long filmId, long userId);

    int getLikesCount(long filmId);

    void deleteFilmFromRating(long filmId);

    boolean removeLike(long filmId, long userId);
}
