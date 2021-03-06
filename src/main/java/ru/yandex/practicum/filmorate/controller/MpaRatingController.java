package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.film.MpaRating;
import ru.yandex.practicum.filmorate.service.MpaRatingService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("mpa")
public class MpaRatingController {
    private final MpaRatingService service;

    @Autowired
    public MpaRatingController(MpaRatingService service) {
        this.service = service;
    }

    @GetMapping
    public List<MpaRating> getAll() {
        log.info("Get all mpa ratings");
        return service.getAll();
    }

    @GetMapping("/{id}")
    public MpaRating getById(@PathVariable("id") int id) {
        MpaRating rating = service.get(id);
        log.info("Get mpa rating {}", rating);
        return rating;
    }
}
