package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.dto.MpaRatingDto;
import ru.yandex.practicum.filmorate.model.film.MpaRating;
import ru.yandex.practicum.filmorate.storage.MpaRatingStorage;
import ru.yandex.practicum.filmorate.util.mapper.MpaRatingMapper;

import java.util.List;

@Service
public class MpaRatingService {
    private final MpaRatingMapper mapper;
    private final MpaRatingStorage storage;

    @Autowired
    public MpaRatingService(MpaRatingStorage storage, MpaRatingMapper mapper) {
        this.mapper = mapper;
        this.storage = storage;
    }

    public MpaRatingDto get(int id) {
        return mapper.ratingToDto(storage.get(id));
    }

    public List<MpaRatingDto> getAll() {
        return mapper.batchRatingToDto(storage.getAll());
    }
}
