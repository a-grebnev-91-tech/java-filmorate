package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.dto.FilmWebDto;
import ru.yandex.practicum.filmorate.model.dto.repo.FilmRepoDto;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.util.mapper.FilmMapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final FilmMapper filmMapper;
    private final FilmRatingService ratingService;
    private final MpaRatingService mpaRatingService;
    private final GenreService genreService;
    private final UserService userService;

    @Autowired
    public FilmService(@Qualifier("filmDBStorage") FilmStorage filmStorage,
                       FilmRatingService ratingService,
                       UserService userService,
                       FilmMapper filmMapper, MpaRatingService mpaRatingService, GenreService genreService) {
        this.filmStorage = filmStorage;
        this.ratingService = ratingService;
        this.userService = userService;
        this.filmMapper = filmMapper;
        this.mpaRatingService = mpaRatingService;
        this.genreService = genreService;
    }

    public void addLike(final long filmId, final long userId) {
        if (!isUserExist(userId)) {
            throw new NotFoundException("User with id " + userId + " isn't exist");
        } else if (!filmStorage.isExist(filmId)) {
            throw new NotFoundException("Film with id " + filmId + "isn't exist");
        } else {
            ratingService.addLike(filmId, userId);
            filmStorage.updateLikesCount(filmId);
        }
    }

    public FilmWebDto createFilm(final FilmWebDto filmWebDto) {
        Film film = filmMapper.webDtoToFilm(filmWebDto);
        FilmRepoDto filmRepoDto = filmMapper.filmToRepoDto(film);
        long createdId = filmStorage.create(filmRepoDto);
        film.setId(createdId);
        genreService.createFilmGenres(createdId, film.getGenres());
        return filmMapper.filmToWebDto(film);
    }

    public FilmWebDto deleteFilm(final long filmId) {
        Film deleted = filmMapper.repoDtoToFilm(filmStorage.get(filmId));
        long deletedId = filmStorage.delete(filmId);
        ratingService.deleteFilm(deletedId);
        genreService.deleteFilmGenres(deletedId);
        return filmMapper.filmToWebDto(deleted);
    }

    public List<FilmWebDto> getAllFilms() {
        return filmStorage
                .getAll()
                .stream()
                .map(repoDto -> filmMapper.repoDtoToFilm(repoDto, getFilmGenres(repoDto.getId()))
                .map(filmMapper::filmToWebDto)
                .collect(Collectors.toList());
    }

    public FilmWebDto getFilm(final long filmId) {
        Film film = filmMapper.repoDtoToFilm(filmStorage.get(filmId), );
        return filmMapper.filmToWebDto(filmStorage.get(filmId));
    }

    public List<FilmWebDto> getTopFilms(final int count) {
        return filmStorage.getTop(count).stream().map(filmMapper::filmToWebDto).collect(Collectors.toList());
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

    public FilmWebDto updateFilm(final FilmWebDto filmWebDto) {
        Film film = filmMapper.webDtoToFilm(filmWebDto);
        return filmMapper.filmToWebDto(filmStorage.update(film));
    }

    private boolean isFilmExist(final long filmId) {
        return filmStorage.isExist(filmId);
    }

    private boolean isUserExist(final long userId) {
        return userService.isUserExist(userId);
    }

    private Set<FilmGenre> getFilmGenres(long filmId) {
        return genreService.getFilmGenres(filmId);
    }
}
