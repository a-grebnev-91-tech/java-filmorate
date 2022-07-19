package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.dto.web.GenreWebDto;
import ru.yandex.practicum.filmorate.service.GenreService;

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
    public List<GenreWebDto> getAll() {
        log.info("Get all genres");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GenreWebDto getById(@PathVariable("id") int id) {
        GenreWebDto genre = service.get(id);
        log.info("Get genre {}", genre);
        return genre;
    }
}
