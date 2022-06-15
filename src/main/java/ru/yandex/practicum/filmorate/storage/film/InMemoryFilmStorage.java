package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.IllegalIdException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

//TODO перенести логирование в контроллер и эксепшн хэндлер
@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private long id;
    private final Map<Long, Film> films;

    public InMemoryFilmStorage() {
        this.films = new HashMap<>();
        this.id = 1;
    }

    @Override
    public Film create(final Film film) {
        if (film.getId() != 0) {
            //todo to exc. handler
            log.warn("Attempt to create film with assigned id");
            throw new IllegalIdException("Cannot create film with assigned id");
        }
        long currentId = generateId();
        film.setId(currentId);
        films.put(currentId, film);
        return film;
    }

    @Override
    public Film delete(final long id) {
        Film film = films.remove(id);
        if (film == null) {
            //todo to exc. handler
            log.warn("Attempt to delete non-existent film with id {}", id);
            throw new NotFoundException("Film with id " + id + " isn't exist.");
        }
        return film;
    }

    @Override
    public Film get(final long id) {
        Film film = films.get(id);
        if (film == null) {
            //todo to exc. handler
            log.warn("Attempt to get non-existing film with id {}", id);
            throw new NotFoundException("Film with id " + id + " isn't exist.");
        }
        return film;
    }

    @Override
    public Collection<Film> getAll() {
        return films.values();
    }

    //TODO check is method goes ok
    @Override
    public Film update(final Film film) {
        long id = film.getId();
        if (films.containsKey(id)) {
            films.put(id, film);
            return film;
        } else {
            //todo to exc. handler
            log.warn("Attempt to update non-existing film");
            throw new IllegalIdException("Id " + id + " isn't exist");
        }
    }

    private long generateId() {
        return id++;
    }
}
