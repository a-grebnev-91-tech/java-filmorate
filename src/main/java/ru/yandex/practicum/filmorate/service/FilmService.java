package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.util.mapper.FilmMapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final FilmMapper mapper;
    private final FilmRatingService ratingService;
    private final UserService userService;

    @Autowired
    public FilmService(@Qualifier("filmDBStorage") FilmStorage filmStorage,
                       FilmRatingService ratingService,
                       UserService userService,
                       FilmMapper mapper) {
        this.filmStorage = filmStorage;
        this.ratingService = ratingService;
        this.userService = userService;
        this.mapper = mapper;
    }

    public void addLike(final long filmId, final long userId) {
        if (isUserExist(userId)) {
            Film film = filmStorage.get(filmId);
            film.addLike();
            ratingService.addLike(filmId, userId);
            filmStorage.update(film);
        } else {
            throw new NotFoundException("User with id " + userId + " isn't exist");
        }
    }

    public FilmDto createFilm(final FilmDto filmDto) {
        Film film = mapper.dtoToFilm(filmDto);
        Film created = filmStorage.create(film);
        return mapper.filmToDto(created);
    }

    public FilmDto deleteFilm(final long filmId) {
        Film deleted = filmStorage.delete(filmId);
        ratingService.deleteFilm(filmId);
        return mapper.filmToDto(deleted);
    }

    public List<FilmDto> getAllFilms() {
        return filmStorage.getAll().stream().map(mapper::filmToDto).collect(Collectors.toList());
    }

    public FilmDto getFilm(final long filmId) {
        return mapper.filmToDto(filmStorage.get(filmId));
    }

    public List<FilmDto> getTopFilms(final int count) {
        return filmStorage.getTop(count).stream().map(mapper::filmToDto).collect(Collectors.toList());
    }

    public void removeLike(final long filmId, final long userId) {
        if (isUserExist(userId)) {
            Film film = filmStorage.get(filmId);
            if (ratingService.removeLike(filmId, userId)) {
                film.removeLike();
                filmStorage.update(film);
            } else {
                throw new NotFoundException("User with id " + userId + " didn't like the movie with id " + filmId);
            }
        } else {
            throw new NotFoundException("User with id " + userId + " isn't exist");
        }
    }

    public FilmDto updateFilm(final FilmDto filmDto) {
        Film film = mapper.dtoToFilm(filmDto);
        return mapper.filmToDto(filmStorage.update(film));
    }

    private boolean isFilmExist(final long filmId) {
        return filmStorage.isExist(filmId);
    }

    private boolean isUserExist(final long userId) {
        return userService.isUserExist(userId);
    }
}
