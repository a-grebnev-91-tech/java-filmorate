package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.dto.MpaRatingDto;
import ru.yandex.practicum.filmorate.model.film.MpaRating;
import ru.yandex.practicum.filmorate.service.MpaRatingService;
import ru.yandex.practicum.filmorate.util.mapper.MpaRatingMapper;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("mpa")
public class MpaRatingController {
    private final MpaRatingMapper mapper;
    private final MpaRatingService service;

    @Autowired
    public MpaRatingController(MpaRatingService service, MpaRatingMapper mapper) {
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping
    public List<MpaRatingDto> getAll() {
        log.info("Get all films");
        return mapper.batchRatingToDto(service.getAll());
    }

    @GetMapping("/{id}")
    public MpaRatingDto getById(@PathVariable("id") int id) {
        MpaRating rating = service.get(id);
        log.info("Get genre {}", rating);
        return mapper.ratingToDto(rating);
    }
}
