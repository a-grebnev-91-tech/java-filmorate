package ru.yandex.practicum.filmorate.storage.in_memory.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.FilmsRating;
import ru.yandex.practicum.filmorate.storage.in_memory.InMemoryDataAttributesStorage;

@Component
public class InMemoryFilmsRatingStorage extends InMemoryDataAttributesStorage<FilmsRating> {
}
