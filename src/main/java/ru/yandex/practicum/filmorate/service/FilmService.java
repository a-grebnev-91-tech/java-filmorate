package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.dto.web.FilmWebDto;
import ru.yandex.practicum.filmorate.model.dto.repo.FilmRepoDto;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;
import ru.yandex.practicum.filmorate.model.film.MpaRating;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final FilmRatingService ratingService;
    private final MpaRatingService mpaRatingService;
    private final GenreService genreService;
    private final UserService userService;

    @Autowired
    public FilmService(@Qualifier("filmDBStorage") FilmStorage filmStorage,
                       FilmRatingService ratingService,
                       UserService userService,
                       MpaRatingService mpaRatingService,
                       GenreService genreService) {
        this.filmStorage = filmStorage;
        this.ratingService = ratingService;
        this.userService = userService;
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
        Film film = webDtoToFilm(filmWebDto);
        Set<FilmGenre> filmGenres = film.getGenres();
        FilmRepoDto filmRepoDto = filmToFilmRepoDto(film);
        long createdId = filmStorage.create(filmRepoDto);
        genreService.addFilmToFilmGenres(createdId, filmGenres);
        film.setId(createdId);
        return filmToWebDto(film);
    }

    public FilmWebDto deleteFilm(final long filmId) {
        Film deleted = getFilmModel(filmId);
        long deletedId = filmStorage.delete(filmId);
        ratingService.deleteFilm(deletedId);
        genreService.deleteFilmFromFilmGenres(deletedId);
        return filmToWebDto(deleted);
    }

    public List<FilmWebDto> getAllFilms() {
        return batchRepoDtoToWebDto(filmStorage.getAll());

    }

    public FilmWebDto getFilm(final long filmId) {
        Film film = getFilmModel(filmId);
        return filmToWebDto(film);
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
        Film film = webDtoToFilm(filmWebDto);
        Set<FilmGenre> genres = film.getGenres();
        filmStorage.update(filmToFilmRepoDto(film));
        genreService.updateFilmGenres(film.getId(), genres);
        return filmToWebDto(film);
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
        return repoDtoToFilm(filmStorage.get(filmId));
    }

    private Film repoDtoToFilm(FilmRepoDto filmRepoDto) {
        MpaRating rating = mpaRatingService.get(filmRepoDto.getMpaRatingId());
        Set<FilmGenre> genres = genreService.getGenreModelByFilm(filmRepoDto.getId());
        return new Film(
                filmRepoDto.getId(),
                filmRepoDto.getName(),
                filmRepoDto.getDescription(),
                filmRepoDto.getReleaseDate(),
                filmRepoDto.getLikesCount(),
                filmRepoDto.getDuration(),
                rating,
                genres
        );
    }

    private List<FilmWebDto> batchRepoDtoToWebDto(List<FilmRepoDto> webDtos) {
        return  webDtos
                .stream()
                .map(repoDto -> getFilmModel(repoDto.getId()))
                .map(this::filmToWebDto)
                .collect(Collectors.toList());
    }

    private FilmRepoDto filmToFilmRepoDto(Film film) {
        return new FilmRepoDto(
                film.getId(),
                film.getDescription(),
                film.getDuration(),
                film.getLikeCount(),
                film.getName(),
                film.getReleaseDate(),
                film.getRating().getId()
        );
    }

    private Film webDtoToFilm(FilmWebDto dto) {
        long id = dto.getId();
        String description = dto.getDescription();
        int duration = dto.getDuration();
        String name = dto.getName();
        LocalDate releaseDate = dto.getReleaseDate();
        MpaRating mpa = dto.getMpa();
        Set<FilmGenre> genres = dto.getGenres() == null ? new HashSet<>() : new HashSet<>(Arrays.asList(dto.getGenres()));
        return new Film(
                id,
                name,
                description,
                releaseDate,
                duration,
                mpa,
                genres
        );
    }

    private FilmWebDto filmToWebDto(Film film) {
        long id = film.getId();
        String description = film.getDescription();
        int duration = film.getDuration();
        String name = film.getName();
        LocalDate releaseDate = film.getReleaseDate();
        MpaRating mpa = film.getRating();
        FilmGenre[] genres = film
                .getGenres()
                .stream()
                .sorted(((o1, o2) -> Integer.compare(o1.getId(), o2.getId())))
                .toArray(FilmGenre[]::new);
        return new FilmWebDto(
                id,
                description,
                duration,
                name,
                releaseDate,
                mpa,
                genres
        );
    }
}
