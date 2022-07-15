package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.film.MpaRating;

import java.util.List;

public interface MpaRatingStorage {
    MpaRating get(int id);
    List<MpaRating> getAll();
}
