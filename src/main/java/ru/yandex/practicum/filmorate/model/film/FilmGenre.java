package ru.yandex.practicum.filmorate.model.film;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
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

    public static FilmGenre getById(int id) {
        FilmGenre[] genres = FilmGenre.values();
        if (id <= genres.length && id > 0) {
            return genres[id - 1];
        } else {
            throw new NotFoundException("Genre id is invalid");
        }
    }

    public static FilmGenre getByTitle(String title) {
        switch (title) {
            case "Комедия":
                return FilmGenre.COMEDY;
            case "Драма":
                return FilmGenre.DRAMA;
            case "Мультфильм":
                return FilmGenre.CARTOON;
            case "Триллер":
                return FilmGenre.THRILLER;
            case "Документальный":
                return FilmGenre.DOCUMENTARY;
            case "Боевик":
                return FilmGenre.ACTION;
            default:
                throw new NotFoundException("Film genre with title " + title + " isn't exist");
        }
    }

    public int getId() {
        return this.ordinal() + 1;
    }
}
