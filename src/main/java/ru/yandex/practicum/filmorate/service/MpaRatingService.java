package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.film.MpaRating;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;

import java.util.List;

@Service
public class MpaRatingService {
    private final MpaRatingStorage storage;

    @Autowired
    public MpaRatingService(MpaRatingStorage storage) {
        this.storage = storage;
    }

    public MpaRating get(int id) {
        return storage.get(id);
    }

    public List<MpaRating> getAll() {
        return storage.getAll();
    }
}
