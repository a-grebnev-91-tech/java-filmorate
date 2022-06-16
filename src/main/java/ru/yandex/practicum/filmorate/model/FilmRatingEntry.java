package ru.yandex.practicum.filmorate.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class FilmRatingEntry extends BaseEntity {
    private final Set<Long> likes;

    public FilmRatingEntry(final long filmId) {
        super(filmId);
        this.likes = new HashSet<>();
    }

    public void addLike(long userId) {
        likes.add(userId);
    }

    public long getFilmId() {
        return getId();
    }

    public Set<Long> getLikes() {
        return Set.copyOf(likes);
    }

    public int rating() {
        return likes.size();
    }

    public void removeLike(long userId) {
        likes.remove(userId);
    }
}
