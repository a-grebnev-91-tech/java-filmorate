package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.util.mapper.FilmMapper;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService service;

    @Autowired
    public FilmController(FilmService service) {
        this.service = service;
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable("id") final long filmId, @PathVariable final long userId) {
        service.addLike(filmId, userId);
        log.info("Added like to film with id = {} by user with id = {}" , filmId, userId);
    }

    @PostMapping
    public FilmDto create(@RequestBody @Valid FilmDto filmDto) {
        FilmDto createdFilm = service.createFilm(filmDto);
        log.info("Create film {}", createdFilm);
        return createdFilm;
    }

    @DeleteMapping("/{id}")
    public FilmDto delete(@PathVariable final long id) {
        FilmDto deletedFilm = service.deleteFilm(id);
        log.info("Delete film {}", deletedFilm);
        return deletedFilm;
    }

    @GetMapping
    public List<FilmDto> findAll() {
        List<FilmDto> films = service.getAllFilms();
        log.info("Get all films");
        return films;
    }

    @GetMapping("/{id}")
    public FilmDto get(@PathVariable final long id){
        FilmDto readFilm = service.getFilm(id);
        log.info("Get {}", readFilm);
        return readFilm;
    }

    @GetMapping("/popular")
    public List<FilmDto> getTop(@RequestParam(defaultValue = "10") final int count) {
        List<FilmDto> top = service.getTopFilms(count);
        log.info("Get top {} films", count);
        return top;
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable("id") final long filmId, @PathVariable final long userId) {
        service.removeLike(filmId, userId);
        log.info("Like has been removed from film with id = {} by user with id = {}", filmId, userId);
    }

    @PutMapping
    public FilmDto update(@RequestBody @Valid FilmDto filmDto) {
        FilmDto updatedFilm = service.updateFilm(filmDto);
        log.info("Update {}", updatedFilm);
        return updatedFilm;
    }
}
