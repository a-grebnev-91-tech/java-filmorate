package ru.yandex.practicum.filmorate.model.film;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
public class FilmDirector {
    @Positive
    private long id;
    @NotEmpty
    private String name;

    @Override
    public String toString() {
        return "FilmDirector{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
