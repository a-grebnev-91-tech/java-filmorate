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
    private final FilmMapper mapper;

    @Autowired
    public FilmController(FilmService service, FilmMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable("id") final long filmId, @PathVariable final long userId) {
        service.addLike(filmId, userId);
        log.info("Added like to film with id = {} by user with id = {}" , filmId, userId);
    }

    @PostMapping
    public FilmDto create(@RequestBody @Valid FilmDto filmDto) {
        Film film = mapper.dtoToFilm(filmDto);
        Film createdFilm = service.createFilm(film);
        log.info("Create film {}", createdFilm);
        return mapper.filmToDto(createdFilm);
    }

    @DeleteMapping("/{id}")
    public FilmDto delete(@PathVariable final long id) {
        Film deletedFilm = service.deleteFilm(id);
        log.info("Delete film {}", deletedFilm);
        return mapper.filmToDto(deletedFilm);
    }

    @GetMapping
    public List<FilmDto> findAll() {
        List<Film> films = service.getAllFilms();
        log.info("Get all films");
        return films.stream().map(mapper::filmToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public FilmDto get(@PathVariable final long id){
        Film readFilm = service.getFilm(id);
        log.info("Get {}", readFilm);
        return mapper.filmToDto(readFilm);
    }

    @GetMapping("/popular")
    public List<FilmDto> getTop(@RequestParam(defaultValue = "10") final int count) {
        List<Film> top = service.getTopFilms(count);
        log.info("Get top {} films", count);
        return top.stream().map(mapper::filmToDto).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable("id") final long filmId, @PathVariable final long userId) {
        service.removeLike(filmId, userId);
        log.info("Like has been removed from film with id = {} by user with id = {}", filmId, userId);
    }

    @PutMapping
    public FilmDto update(@RequestBody @Valid FilmDto filmDto) {
        Film film = mapper.dtoToFilm(filmDto);
        Film updatedFilm = service.updateFilm(film);
        log.info("Update {}", updatedFilm);
        return mapper.filmToDto(updatedFilm);
    }
}
