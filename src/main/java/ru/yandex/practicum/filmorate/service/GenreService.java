package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.dto.web.GenreWebDto;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.util.mapper.GenreMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GenreService {
    private final GenreMapper mapper;
    private final GenreStorage storage;

    @Autowired
    public GenreService(GenreStorage storage, GenreMapper mapper) {
        this.mapper = mapper;
        this.storage = storage;
    }

    public void addFilmToFilmGenres(long filmId, Set<FilmGenre> genres) {
        storage.addFilm(filmId, genres);
    }

    public void deleteFilmFromFilmGenres(long filmId) {
        storage.deleteFilm(filmId);
    }

    public GenreWebDto get(int id) {
        return mapper.genreToDto(storage.get(id));
    }

    public List<GenreWebDto> getAll() {
        return mapper.batchGenreToDto(storage.getAll());
    }

    public Set<FilmGenre> getGenreModelByFilm(long filmId) {
        return new HashSet<>(storage.getGenresByFilm(filmId));
    }

    public void updateFilmGenres(long filmId, Set<FilmGenre> genres) {
        storage.updateFilm(filmId, genres);
    }
}
