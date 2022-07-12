package ru.yandex.practicum.filmorate.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmGenreDto {
    private int id;
    private String name;
}