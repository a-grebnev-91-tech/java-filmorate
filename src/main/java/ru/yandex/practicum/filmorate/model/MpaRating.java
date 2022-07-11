package ru.yandex.practicum.filmorate.model;

import ru.yandex.practicum.filmorate.exception.ValidationException;

public enum MpaRating {
    G("G"),
    PG("PG"),
    PG13("PG-13"),
    R("R"),
    NC17("NC-17");
    private final String title;

    MpaRating(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static MpaRating getByOrdinal(int ordinal) {
        MpaRating[] ratings = MpaRating.values();
        if (ordinal <= ratings.length && ordinal > 0) {
            return ratings[ordinal - 1];
        } else {
            throw new ValidationException("Mpa rating id is invalid");
        }
    }
}
