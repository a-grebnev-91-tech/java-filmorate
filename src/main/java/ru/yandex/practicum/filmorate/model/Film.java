package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.util.ValidReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class Film extends BaseEntity{
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

    public Film() {}

    public Film(long id, String name, String description, LocalDate releaseDate, int duration) {
        super(id);
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
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
