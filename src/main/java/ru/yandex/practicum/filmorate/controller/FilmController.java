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
public class FilmController extends BaseController<Film> {

    @Autowired
    public FilmController(FilmService service) {
        super(service);
    }

    @PostMapping
    @Override
    public Film create(@RequestBody @Valid Film film) {
        return super.create(film);
    }

    @DeleteMapping("/{id}")
    @Override
    public Film delete(@PathVariable final long id) {
        return super.delete(id);
    }


    @GetMapping
    @Override
    public Collection<Film> findAll() {
        Collection<Film> films = super.findAll();
        log.info("Get all films");
        return  films;
    }

    @GetMapping("/{id}")
    @Override
    public Film get(@PathVariable final long id){
        return super.get(id);
    }

    @PutMapping
    @Override
    public Film update(@RequestBody @Valid Film film) {
        return super.update(film);
    }
}
