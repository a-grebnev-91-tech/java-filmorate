package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.FilmEntry;
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
    public FilmEntry create(@RequestBody @Valid FilmEntry film) {
        FilmEntry createdFilm = service.create(film);
        log.info("Create film {}", createdFilm);
        return createdFilm;
    }

    @DeleteMapping("/{id}")
    public FilmEntry delete(@PathVariable final long id) {
        FilmEntry deletedFilm = service.delete(id);
        log.info("Delete film {}", deletedFilm);
        return deletedFilm;
    }

    @GetMapping
    public Collection<FilmEntry> findAll() {
        Collection<FilmEntry> films = service.getAll();
        log.info("Get all films");
        return  films;
    }

    @GetMapping("/{id}")
    public FilmEntry get(@PathVariable final long id){
        FilmEntry readFilm = service.get(id);
        log.info("Get {}", readFilm);
        return readFilm;
    }

    //todo check if spring can parse string to int
    @GetMapping("/popular")
    public List<FilmEntry> getTop(@RequestParam(defaultValue = "10") final int count) {
        List<FilmEntry> top = service.getTopFilms(count);
        log.info("Get top " + count + " films");
        return top;
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable("id") final long filmId, @PathVariable final long userId) {
        service.removeLike(filmId, userId);
        log.info("Like has been removed from film with id = " + filmId + " by user with id = " + userId);
    }

    @PutMapping
    public FilmEntry update(@RequestBody @Valid FilmEntry film) {
        FilmEntry updatedFilm = service.update(film);
        log.info("Update {}", updatedFilm);
        return updatedFilm;
    }
}
