package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.util.ValidReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Film {
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
    @EqualsAndHashCode.Include
    private long id;
    private final Set<Long> likes;
    @Getter
    @Setter
    @NotBlank(message = "Name shouldn't be null or empty")
    private String name;
    @Getter
    @Setter
    @ValidReleaseDate(message = "Release date should be after 1895.12.28")
    private LocalDate releaseDate;

    public Film() {
        this.likes = new HashSet<>();
    }

    public Film(long id, String name, String description, LocalDate releaseDate, int duration) {
        this();
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }

    public boolean addLike(long userId) {
        return likes.add(userId);
    }

    public int rating() {
        return likes.size();
    }

    public Set<Long> getLikes() {
        return Set.copyOf(likes);
    }
}
