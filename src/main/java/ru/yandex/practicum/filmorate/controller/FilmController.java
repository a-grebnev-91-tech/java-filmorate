package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.film.Film;
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
        log.info("Added like to film with id = " + filmId + " by user with id =" + userId);
    }

    @PostMapping
    public Film create(@RequestBody @Valid Film film) {
        Film createdFilm = service.createFilm(film);
        log.info("Create film {}", createdFilm);
        return createdFilm;
    }

    @DeleteMapping("/{id}")
    public Film delete(@PathVariable final long id) {
        Film deletedFilm = service.deleteFilm(id);
        log.info("Delete film {}", deletedFilm);
        return deletedFilm;
    }

    @GetMapping
    public List<Film> findAll() {
        List<Film> films = service.getAllFilms();
        log.info("Get all films");
        return  films;
    }

    @GetMapping("/{id}")
    public Film get(@PathVariable final long id){
        Film readFilm = service.getFilm(id);
        log.info("Get {}", readFilm);
        return readFilm;
    }

    @GetMapping("/popular")
    public List<Film> getTop(@RequestParam(defaultValue = "10") final int count) {
        List<Film> top = service.getTopFilms(count);
        log.info("Get top " + count + " films");
        return top;
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable("id") final long filmId, @PathVariable final long userId) {
        service.removeLike(filmId, userId);
        log.info("Like has been removed from film with id = " + filmId + " by user with id = " + userId);
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        Film updatedFilm = service.updateFilm(film);
        log.info("Update {}", updatedFilm);
        return updatedFilm;
    }
}
