package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.film.FilmDirector;
import ru.yandex.practicum.filmorate.storage.DirectorStorage;

import java.util.List;

@Service
public class DirectorService {
    private final DirectorStorage storage;

    @Autowired
    public DirectorService(DirectorStorage storage) {
        this.storage = storage;
    }


    public FilmDirector createDirector(FilmDirector director) {
        long createdId = storage.create(director);
        director.setId(createdId);
        return director;
    }

    public FilmDirector delete(long id) {
        return storage.delete(id);
    }

    public FilmDirector get(long id) {
        return storage.get(id);
    }

    public List<FilmDirector> getAll() {
        return storage.getAll();
    }

    public FilmDirector update(FilmDirector director) {
        storage.update(director);
        return director;
    }
}
