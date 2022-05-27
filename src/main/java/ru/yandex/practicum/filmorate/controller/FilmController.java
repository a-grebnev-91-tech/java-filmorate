package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final Set<Film> films = new HashSet<>();

    @PostMapping
    @PutMapping
    public Film create(@RequestBody @Valid Film film) {
        films.add(film);
        return film;
    }

    @GetMapping
    public Set<Film> findAll() {
        return films;
    }
}
