package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IllegalIdException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();
    private long id = 1;

    @PostMapping
    public Film create(@RequestBody @Valid Film film) {
        if (film.getId() != 0) {
            log.warn("Attempt to create film with assigned id");
            throw new IllegalIdException("Cannot create film with assigned id");
        }
        long currentId = generateId();
        film.setId(currentId);
        log.info("Add film {}", film);
        films.put(currentId, film);
        return film;
    }

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        long id = film.getId();
        if (films.containsKey(id)) {
            log.info("Update film {}", film);
            films.put(id, film);
            return film;
        }
        log.warn("Attempt to update non-existing film");
        throw new IllegalIdException("Id " + id + " isn't exist");
    }

    private long generateId() {
        return id++;
    }
}
