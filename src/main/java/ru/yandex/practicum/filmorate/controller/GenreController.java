package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;
import ru.yandex.practicum.filmorate.model.dto.FilmGenreDto;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.util.mapper.GenreMapper;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GenreService service;

    @Autowired
    public GenreController(GenreService service) {
        this.service = service;
    }

    @GetMapping
    public List<FilmGenreDto> getAll() {
        log.info("Get all genres");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public FilmGenreDto getById(@PathVariable("id") int id) {
        FilmGenreDto genre = service.get(id);
        log.info("Get genre {}", genre);
        return genre;
    }
}
