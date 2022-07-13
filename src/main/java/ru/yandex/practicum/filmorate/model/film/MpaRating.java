package ru.yandex.practicum.filmorate.model.film;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
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

    public static MpaRating getById(int id) {
        MpaRating[] ratings = MpaRating.values();
        if (id <= ratings.length && id > 0) {
            return ratings[id - 1];
        } else {
            throw new ValidationException("Mpa rating id is invalid");
        }
    }

    public static MpaRating getByTitle(String title) {
        switch (title.toUpperCase().trim()) {
            case "G":
                return MpaRating.G;
            case "PG":
                return MpaRating.PG;
            case "PG-13":
                return MpaRating.PG13;
            case "R":
                return MpaRating.R;
            case "NC-17":
                return MpaRating.NC17;
            default:
                throw new NotFoundException("Mpa rating with title " + title + " isn't exist");
        }
    }

    public int getId() {
        return this.ordinal() + 1;
    }
}
