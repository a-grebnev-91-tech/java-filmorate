package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.FilmData;
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
    public FilmData create(@RequestBody @Valid FilmData film) {
        FilmData createdFilm = service.create(film);
        log.info("Create film {}", createdFilm);
        return createdFilm;
    }

    @DeleteMapping("/{id}")
    public FilmData delete(@PathVariable final long id) {
        FilmData deletedFilm = service.delete(id);
        log.info("Delete film {}", deletedFilm);
        return deletedFilm;
    }

    @GetMapping
    public Collection<FilmData> findAll() {
        Collection<FilmData> films = service.getAll();
        log.info("Get all films");
        return  films;
    }

    @GetMapping("/{id}")
    public FilmData get(@PathVariable final long id){
        FilmData readFilm = service.get(id);
        log.info("Get {}", readFilm);
        return readFilm;
    }

    //todo check if spring can parse string to int
    @GetMapping("/popular")
    public List<FilmData> getTop(@RequestParam(defaultValue = "10") final int count) {
        List<FilmData> top = service.getTopFilms(count);
        log.info("Get top " + count + " films");
        return top;
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable("id") final long filmId, @PathVariable final long userId) {
        service.removeLike(filmId, userId);
        log.info("Like has been removed from film with id = " + filmId + " by user with id = " + userId);
    }

    @PutMapping
    public FilmData update(@RequestBody @Valid FilmData film) {
        FilmData updatedFilm = service.update(film);
        log.info("Update {}", updatedFilm);
        return updatedFilm;
    }
}
