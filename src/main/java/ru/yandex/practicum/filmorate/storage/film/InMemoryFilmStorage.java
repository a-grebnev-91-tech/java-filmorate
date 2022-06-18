package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryDataStorage;

@Component
public class InMemoryFilmStorage extends InMemoryDataStorage<Film> {
}
