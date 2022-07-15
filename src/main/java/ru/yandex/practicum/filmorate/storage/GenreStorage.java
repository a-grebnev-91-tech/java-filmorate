package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.film.FilmGenre;

import java.util.List;

public interface GenreStorage {
    List<FilmGenre> getAll();
    FilmGenre get(int id);
}
