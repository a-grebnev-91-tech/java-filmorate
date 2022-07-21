package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.film.FilmDirector;
import ru.yandex.practicum.filmorate.storage.DirectorStorage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DirectorService {
    private final DirectorStorage storage;

    @Autowired
    public DirectorService(DirectorStorage storage) {
        this.storage = storage;
    }

    public void addDirectorToFilm(long filmId, long directorId) {
        storage.addDirectorToFilm(filmId, directorId);
    }

    public FilmDirector createDirector(FilmDirector director) {
        checkName(director);
        long createdId = storage.createDirector(director);
        director.setId(createdId);
        return director;
    }

    public FilmDirector deleteDirector(long id) {
        return storage.deleteDirector(id);
    }

    public void deleteFilmFromFilmsDirectors(long deletedId) {
        storage.deleteFilmFromFilmsDirectors(deletedId);
    }

    public FilmDirector getDirector(long id) {
        return storage.getDirector(id);
    }

    public List<FilmDirector> getDirectorsByFilm(long filmId) {
        List<FilmDirector> directors = storage.getDirectorsByFilm(filmId);
        return directors;
    }

    public List<Long> getFilmsByDirector(long directorId) {
        return storage.getFilmsByDirectors(directorId);
    }

    public List<FilmDirector> getAllDirectors() {
        return storage.getAllDirectors();
    }

    public List<FilmDirector> getSomeById(List<Long> directorsIds) {
        return storage.getSomeDirectors(directorsIds);
    }

    public boolean isDirectorExist(long directorId) {
        try {
            getDirector(directorId);
            return true;
        } catch (NotFoundException ex) {
            return false;
        }
    }

    public FilmDirector updateDirector(FilmDirector director) {
        checkName(director);
        if (isDirectorExist(director.getId())) {
            storage.updateDirector(director);
            return director;
        } else {
            throw new NotFoundException(String.format("Director with id %d isn't exist", director.getId()));
        }
    }

    public void updateFilmDirector(long filmId, Set<FilmDirector> directors) {
        storage.updateFilmDirector(filmId, directors.stream().map(FilmDirector::getId).collect(Collectors.toList()));
    }

    private void checkName(FilmDirector director) {
        if (director.getName() == null || director.getName().isBlank()) {
            throw new ValidationException("Director's name shouldn't be empty");
        }
    }
}
