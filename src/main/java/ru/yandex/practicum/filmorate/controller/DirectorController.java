package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.film.FilmDirector;
import ru.yandex.practicum.filmorate.service.DirectorService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/directors")
public class DirectorController {
    private final DirectorService service;

    @Autowired
    public DirectorController(DirectorService service) {
        this.service = service;
    }

    @PostMapping
    public FilmDirector create(@RequestBody FilmDirector director) {
        FilmDirector created = service.createDirector(director);
        log.info("Created {}", created);
        return created;
    }

    @DeleteMapping("/{id}")
    public FilmDirector delete(@PathVariable long id) {
        FilmDirector deleted = service.delete(id);
        log.info("Delete ", deleted);
        return deleted;
    }

    @GetMapping("/{id}")
    public FilmDirector get(@PathVariable long id) {
        FilmDirector received = service.get(id);
        log.info("Received {}", received);
        return received;
    }

    @GetMapping
    public List<FilmDirector> getAll() {
        log.info("Received all directors");
        return service.getAll();
    }

    @PutMapping
    public FilmDirector update(@RequestBody FilmDirector director) {
        FilmDirector updated = service.update(director);
        log.info("Update {}", updated);
        return updated;
    }
}
