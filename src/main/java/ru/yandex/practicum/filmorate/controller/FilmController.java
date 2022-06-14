package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService service;
    private final Map<Long, Film> films;

    @Autowired
    public FilmController(FilmService service) {
        this.service = service;
        this.films = new HashMap<>();
    }

    @PostMapping
    public Film create(@RequestBody @Valid Film film) {
        return service.create(film);
    }

    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        return service.update(film);
    }


}
