package ru.yandex.practicum.filmorate.util;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.dto.FilmGenreDto;
import ru.yandex.practicum.filmorate.model.dto.MpaRatingDto;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class FilmMapper {

    public Film filmDtoToFilm(FilmDto dto) {
        long id = dto.getId();
        String description = dto.getDescription();
        int duration = dto.getDuration();
        String name = dto.getName();
        LocalDate releaseDate = dto.getReleaseDate();
        MpaRating mpaRating = MpaRating.getById(dto.getMpa().getId());
        Set<FilmGenre> genres;
        FilmGenreDto[] genreDtos = dto.getGenres();
        if (genreDtos != null) {
            genres = Arrays
                    .stream(genreDtos)
                    .map(genreDto -> FilmGenre.getById(genreDto.getId()))
                    .collect(Collectors.toSet());
        } else {
            genres = new HashSet<>();
        }
        return new Film(id, name, description, releaseDate, duration, mpaRating, genres);
    }

    public FilmDto filmToFilmDto(Film film) {
        long id = film.getId();
        String description = film.getDescription();
        int duration = film.getDuration();
        String name = film.getName();
        LocalDate releaseDate = film.getReleaseDate();
        MpaRatingDto mpaDto = new MpaRatingDto(film.getRating().getId(), film.getRating().getTitle());
        FilmGenreDto[] genreDtos =
                film
                .getGenres()
                .stream().map(genre -> new FilmGenreDto(genre.getId(), genre.getTitle()))
                .toArray(FilmGenreDto[]::new);
        return new FilmDto(id, description, duration, name, releaseDate, mpaDto, genreDtos);
    }
}
