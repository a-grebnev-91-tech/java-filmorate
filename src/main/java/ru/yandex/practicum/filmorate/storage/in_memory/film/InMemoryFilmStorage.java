package ru.yandex.practicum.filmorate.storage.in_memory.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.in_memory.InMemoryDataStorage;

@Component("inMemoryFilmStorage")
public class InMemoryFilmStorage extends InMemoryDataStorage<Film> {
}
