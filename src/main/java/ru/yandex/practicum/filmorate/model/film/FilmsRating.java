package ru.yandex.practicum.filmorate.model.film;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.DataAttributes;

import java.util.Set;

public class FilmsRating extends DataAttributes<Long> {

    public FilmsRating(long filmId) {
        super(filmId);
    }

    public boolean addLike(long userId) {
        return super.addAttribute(userId);
    }

    public int rating() {
        return super.attributesCount();
    }

    public long getFilmId() {
        return super.getDataId();
    }

    public Set<Long> getLikes() {
        return super.getAttributes();
    }

    public boolean isLiked(long userId) {
        return super.isAttributePresent(userId);
    }

    public boolean removeLike(long userId) {
        if (isLiked(userId)) {
            return super.removeAttribute(userId);
        } else {
            throw new NotFoundException("User with id " + userId + " doesn't liked film with id " + getFilmId());
        }
    }
}
