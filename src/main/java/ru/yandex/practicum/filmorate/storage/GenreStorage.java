package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.film.FilmGenre;

import java.util.List;
import java.util.Set;

public interface GenreStorage {
    void addFilm(long filmId, Set<FilmGenre> genres);

    List<FilmGenre> getAll();

    FilmGenre get(int id);

    List<FilmGenre> getGenresByFilm(long filmId);

    void deleteFilm(long filmId);

    void updateFilm(long filmId, Set<FilmGenre> genres);
}
