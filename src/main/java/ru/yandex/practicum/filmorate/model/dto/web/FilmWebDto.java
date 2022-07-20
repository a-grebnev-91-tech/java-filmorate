package ru.yandex.practicum.filmorate.model.dto.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;
import ru.yandex.practicum.filmorate.model.film.MpaRating;
import ru.yandex.practicum.filmorate.util.validator.ValidReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class FilmWebDto {
    private long id;
    @Size(max = 200, message = "Description shouldn't be larger than 200 characters")
    private String description;
    @Positive(message = "Duration should be greater than 0")
    private int duration;
    @NotBlank(message = "Name shouldn't be null or empty")
    private String name;
    @ValidReleaseDate(message = "Release date should be after 1895.12.28")
    private LocalDate releaseDate;
    @NotNull(message = "Mpa rating shouldn't be null")
    private MpaRating mpa;
    private FilmGenre[] genres;
}
