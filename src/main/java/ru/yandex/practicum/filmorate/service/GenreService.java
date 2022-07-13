package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.List;

@Service
public class GenreService {
    private final GenreStorage storage;

    @Autowired
    public GenreService(GenreStorage storage) {
        this.storage = storage;
    }

    public List<FilmGenre> getAll() {
        return storage.getAll();
    }

    public FilmGenre get(int id) {
        return storage.get(id);
    }
}
