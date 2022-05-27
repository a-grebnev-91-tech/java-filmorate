package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final Set<Film> films = new HashSet<>();

    @PostMapping
    public Film create(@RequestBody Film film) {
        if (films.add(film))
            return film;
        else
            throw new RuntimeException(); //TODO fix this
    }

    @GetMapping
    public Set<Film> findAll() {
        return films;
    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        if (films.add(film))
            return film;
        else
            throw new RuntimeException(); //TODO fix this
    }
}
