package ru.yandex.practicum.filmorate.model.film;

import lombok.*;
import ru.yandex.practicum.filmorate.model.BaseData;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.MPARating;
import ru.yandex.practicum.filmorate.util.ValidReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

public class Film extends BaseData {
    @Getter
    @Setter
    @Size(max = 200, message = "Description shouldn't be larger than 200 characters")
    private String description;
    @Getter
    @Setter
    @Positive(message = "Duration should be greater than 0")
    private int duration;
    @Getter
    @Setter
    @NotBlank(message = "Name shouldn't be null or empty")
    private String name;
    @Getter
    @Setter
    @ValidReleaseDate(message = "Release date should be after 1895.12.28")
    private LocalDate releaseDate;
    @Getter
    @Setter
    private MPARating rating;
    @Getter
    @Setter
    private Set<FilmGenre> genres;

    public Film() {}

    public Film(long id, String name, String description, LocalDate releaseDate, int duration) {
        super(id);
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public Film(long id,
                String name,
                String description,
                LocalDate releaseDate,
                int duration,
                MPARating rating,
                Set<FilmGenre> genres) {
        this(id, name, description, releaseDate, duration);
        this.rating = rating;
        this.genres = genres;
    }

    public void addGenre(FilmGenre genre) {
        genres.add(genre);
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + getId() +
                "duration=" + duration +
                ", name='" + name + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
