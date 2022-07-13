package ru.yandex.practicum.filmorate.model.film;

import lombok.*;
import ru.yandex.practicum.filmorate.model.BaseData;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Film extends BaseData {
    @Getter
    @Setter
    private String description;
    @Getter
    @Setter
    private int duration;
    @Getter
    @Setter
    private int likeCount;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private LocalDate releaseDate;
    @Getter
    @Setter
    private MpaRating rating;
    @Getter
    @Setter
    private Set<FilmGenre> genres;

    public Film() {
        genres = new HashSet<>();
    }

    public Film(long id, String name, String description, LocalDate releaseDate, int duration) {
        super(id);
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        genres = new HashSet<>();
    }

    public Film(long id,
                String name,
                String description,
                LocalDate releaseDate,
                int duration,
                MpaRating rating,
                Set<FilmGenre> genres) {
        this(id, name, description, releaseDate, duration);
        this.rating = rating;
        if (genres == null) {
            genres = new HashSet<>();
        } else {
            this.genres = genres;
        }
    }

    public Film(long id,
                String name,
                String description,
                LocalDate releaseDate,
                int likeCount,
                int duration,
                MpaRating rating,
                Set<FilmGenre> genres) {
        this(id, name, description, releaseDate, duration, rating, genres);
        this.likeCount = likeCount;
    }

    public void addGenre(FilmGenre genre) {
        genres.add(genre);
    }

    public void addLike() {
        likeCount++;
    }

    public void removeLike() {
        likeCount--;
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
