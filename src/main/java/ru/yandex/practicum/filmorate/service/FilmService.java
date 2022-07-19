package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.dto.web.FilmWebDto;
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
        } else if (!isFilmExist(filmId)) {
            throw new NotFoundException("Film with id " + filmId + "isn't exist");
        } else {
            ratingService.addLike(filmId, userId);
            filmStorage.updateLikesCount(filmId);
        }
    }

    //todo think about refactoring
    public FilmWebDto createFilm(final FilmWebDto filmWebDto) {
        Film film = filmMapper.webDtoToFilm(filmWebDto);
        Set<FilmGenre> filmGenres = film.getGenres();
        FilmRepoDto filmRepoDto = filmMapper.filmToRepoDto(film);
        long createdId = filmStorage.create(filmRepoDto);
        genreService.addFilmToFilmGenres(createdId, filmGenres);
        film.setId(createdId);
        return filmMapper.filmToWebDto(film);
    }

    public FilmWebDto deleteFilm(final long filmId) {
        Film deleted = getFilmModel(filmId);
        long deletedId = filmStorage.delete(filmId);
        ratingService.deleteFilm(deletedId);
        genreService.deleteFilmFromFilmGenres(deletedId);
        return filmMapper.filmToWebDto(deleted);
    }

    public List<FilmWebDto> getAllFilms() {
        return batchRepoDtoToWebDto(filmStorage.getAll());

    }

    public FilmWebDto getFilm(final long filmId) {
        Film film = getFilmModel(filmId);
        return filmMapper.filmToWebDto(film);
    }

    public List<FilmWebDto> getTopFilms(final int count) {
        return batchRepoDtoToWebDto(filmStorage.getTop(count));
    }

    public void removeLike(final long filmId, final long userId) {
        if (!isUserExist(userId)) {
            throw new NotFoundException("User with id " + userId + " isn't exist");
        } else if (!isFilmExist(filmId)) {
            throw new NotFoundException("Film with id " + filmId + "isn't exist");
        } else {
            ratingService.removeLike(filmId, userId);
            filmStorage.updateLikesCount(filmId);
        }
    }

    public FilmWebDto updateFilm(final FilmWebDto filmWebDto) {
        Film film = filmMapper.webDtoToFilm(filmWebDto);
        Set<FilmGenre> genres = film.getGenres();
        genreService.updateFilmGenres(film.getId(), genres);
        filmStorage.update(filmMapper.filmToRepoDto(film));
        return filmMapper.filmToWebDto(film)
                ;
    }

    private boolean isFilmExist(final long filmId) {
        return filmStorage.isExist(filmId);
    }

    private boolean isUserExist(final long userId) {
        return userService.isUserExist(userId);
    }

    private Set<FilmGenre> getFilmGenres(long filmId) {
        return genreService.getGenreModelByFilm(filmId);
    }

    private Film getFilmModel(final long filmId) {
        return filmMapper.repoDtoToFilm(filmStorage.get(filmId), genreService.getGenreModelByFilm(filmId));
    }

    private List<FilmWebDto> batchRepoDtoToWebDto(List<FilmRepoDto> webDtos) {
        return  webDtos
                .stream()
                .map(repoDto -> getFilmModel(repoDto.getId()))
                .map(filmMapper::filmToWebDto)
                .collect(Collectors.toList());
    }
}
