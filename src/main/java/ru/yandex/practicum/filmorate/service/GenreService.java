package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GenreService {
    private final GenreStorage storage;

    @Autowired
    public GenreService(GenreStorage storage) {
        this.storage = storage;
    }

    public void addFilmToFilmGenres(long filmId, Set<FilmGenre> genres) {
        storage.addFilm(filmId, genres);
    }

    public void deleteFilmFromFilmGenres(long filmId) {
        storage.deleteFilm(filmId);
    }

    public FilmGenre get(int id) {
        return storage.get(id);
    }

    public List<FilmGenre> getAll() {
        return storage.getAll();
    }

    public Set<FilmGenre> getGenresByFilm(long filmId) {
        return new HashSet<>(storage.getGenresByFilm(filmId));
    }

    public void updateFilmGenres(long filmId, Set<FilmGenre> genres) {
        storage.updateFilm(filmId, genres);
    }
}
