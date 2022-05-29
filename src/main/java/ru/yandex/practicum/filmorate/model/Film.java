package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.util.ValidReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Film {
    @EqualsAndHashCode.Include
    private long id;
    @NotBlank(message = "Name shouldn't be null or empty")
    private String name;
    @Size(max = 200, message = "Description shouldn't be larger than 200 characters")
    private String description;
    @ValidReleaseDate(message = "Release date should be after 1895.12.28")
    private LocalDate releaseDate;
    @Positive(message = "Duration should be greater than 0")
    private int duration;
}
