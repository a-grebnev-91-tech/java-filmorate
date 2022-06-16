package ru.yandex.practicum.filmorate.model;

import java.util.HashSet;
import java.util.Set;

public class FilmRatingEntry extends BaseEntry {
    private final Set<Long> likes;

    public FilmRatingEntry(final long filmId) {
        super(filmId);
        this.likes = new HashSet<>();
    }

    public void addLike(final long userId) {
        likes.add(userId);
    }

    public long getFilmId() {
        return getId();
    }

    public Set<Long> getLikes() {
        return Set.copyOf(likes);
    }

    public boolean isLiked(final long userId) {
        return likes.contains(userId);
    }

    public int rating() {
        return likes.size();
    }

    public void removeLike(final long userId) {
        likes.remove(userId);
    }
}
