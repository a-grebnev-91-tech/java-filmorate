package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.List;

public interface FilmStorage extends DataStorage<Film>{
    List<Film> getTop(int count);

}
