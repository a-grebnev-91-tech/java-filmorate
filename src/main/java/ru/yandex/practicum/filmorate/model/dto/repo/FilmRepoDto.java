package ru.yandex.practicum.filmorate.model.dto.repo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FilmRepoDto {
    private final long id;
    private final String description;
    private final int duration;
    private final int likesCount;
    private final String name;
    private final LocalDate releaseDate;
    private final int mpaRatingId;
}
