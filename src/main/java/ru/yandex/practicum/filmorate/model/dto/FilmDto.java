package ru.yandex.practicum.filmorate.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class FilmDto {
    private long id;
    private String description;
    private int duration;
    private String name;
    private LocalDate releaseDate;
    private MpaRatingDto mpa;
    private FilmGenreDto[] genres;
}
