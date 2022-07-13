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
    private final GenreMapper mapper;
    private final GenreService service;

    @Autowired
    public GenreController(GenreService service, GenreMapper mapper) {
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping
    public List<FilmGenreDto> getAll() {
        log.info("Get all films");
        return mapper.batchGenreToDto(service.getAll());
    }

    @GetMapping("/{id}")
    public FilmGenreDto getById(@PathVariable("id") int id) {
        FilmGenre genre = service.get(id);
        log.info("Get genre {}", genre);
        return mapper.genreToDto(genre);
    }
}
