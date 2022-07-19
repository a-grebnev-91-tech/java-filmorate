package ru.yandex.practicum.filmorate.model.dto.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MpaRatingWebDto {
    private int id;
    private String name;
}
