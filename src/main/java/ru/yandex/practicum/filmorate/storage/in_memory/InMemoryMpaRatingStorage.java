package ru.yandex.practicum.filmorate.storage.in_memory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.MpaRating;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InMemoryMpaRatingStorage implements MpaRatingStorage {
    @Override
    public MpaRating get(int id) {
        return MpaRating.getById(id);
    }

    @Override
    public List<MpaRating> getAll() {
        MpaRating[] values = MpaRating.values();
        return Arrays.stream(values).collect(Collectors.toList());
    }
}
