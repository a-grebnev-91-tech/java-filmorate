package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.dto.web.FilmWebDto;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.*;

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
    public FilmWebDto create(@RequestBody @Valid FilmWebDto filmWebDto) {
        FilmWebDto createdFilm = service.createFilm(filmWebDto);
        log.info("Create film {}", createdFilm);
        return createdFilm;
    }

    @DeleteMapping("/{id}")
    public FilmWebDto delete(@PathVariable final long id) {
        FilmWebDto deletedFilm = service.deleteFilm(id);
        log.info("Delete film {}", deletedFilm);
        return deletedFilm;
    }

    @GetMapping
    public List<FilmWebDto> findAll() {
        List<FilmWebDto> films = service.getAllFilms();
        log.info("Get all films");
        return films;
    }

    @GetMapping("/{id}")
    public FilmWebDto get(@PathVariable final long id){
        FilmWebDto readFilm = service.getFilm(id);
        log.info("Get {}", readFilm);
        return readFilm;
    }

    @GetMapping("/director/{directorId}") //year, likes
    public List<FilmWebDto> getByDirectors(@PathVariable long directorId, @RequestParam String sortBy) {
        List<FilmWebDto> result = service.getFilmsByDirector(directorId, sortBy);
        log.info("Get films by director with id {}", directorId);
        return result;
    }

    @GetMapping("/popular")
    public List<FilmWebDto> getTop(@RequestParam(defaultValue = "10") final int count) {
        List<FilmWebDto> top = service.getTopFilms(count);
        log.info("Get top {} films", count);
        return top;
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable("id") final long filmId, @PathVariable final long userId) {
        service.removeLike(filmId, userId);
        log.info("Like has been removed from film with id = {} by user with id = {}", filmId, userId);
    }

    @PutMapping
    public FilmWebDto update(@RequestBody @Valid FilmWebDto filmWebDto) {
        FilmWebDto updatedFilm = service.updateFilm(filmWebDto);
        log.info("Update {}", updatedFilm);
        return updatedFilm;
    }
}
