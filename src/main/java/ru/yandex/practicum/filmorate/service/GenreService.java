package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.dto.FilmGenreDto;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.util.mapper.GenreMapper;

import java.util.List;

@Service
public class GenreService {
    private final GenreMapper mapper;
    private final GenreStorage storage;

    @Autowired
    public GenreService(GenreStorage storage, GenreMapper mapper) {
        this.mapper = mapper;
        this.storage = storage;
    }

    public List<FilmGenreDto> getAll() {
        return mapper.batchGenreToDto(storage.getAll());
    }

    public FilmGenreDto get(int id) {
        return mapper.genreToDto(storage.get(id));
    }
}
