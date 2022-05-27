package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {
    private final long id;
    private final String name;
    private final String description;
    private LocalDate releaseDate;
    private Duration duration;
}
