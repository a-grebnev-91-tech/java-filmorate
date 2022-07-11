package ru.yandex.practicum.filmorate.model;

import ru.yandex.practicum.filmorate.exception.ValidationException;

public enum FilmGenre {
    COMEDY("Комедия"),
    DRAMA("Драма"),
    CARTOON("Мультфильм"),
    THRILLER("Триллер"),
    DOCUMENTARY("Документальный"),
    ACTION("Боевик");

    private final String title;
    FilmGenre(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static FilmGenre getByOrdinal(int ordinal) {
        FilmGenre[] genres = FilmGenre.values();
        if (ordinal <= genres.length && ordinal > 0) {
            return genres[ordinal - 1];
        } else {
            throw new ValidationException("Genre id is invalid");
        }
    }
}
