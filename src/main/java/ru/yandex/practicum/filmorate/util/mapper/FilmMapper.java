package ru.yandex.practicum.filmorate.util.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;
import ru.yandex.practicum.filmorate.model.film.MpaRating;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.dto.FilmGenreDto;
import ru.yandex.practicum.filmorate.model.dto.MpaRatingDto;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class FilmMapper {
    private final GenreMapper genreMapper;
    private final MpaRatingMapper mpaMapper;

    @Autowired
    public FilmMapper(GenreMapper genreMapper, MpaRatingMapper mpaRatingMapper) {
        this.genreMapper = genreMapper;
        this.mpaMapper = mpaRatingMapper;
    }

    public Film dtoToFilm(FilmDto dto) {
        long id = dto.getId();
        String description = dto.getDescription();
        int duration = dto.getDuration();
        String name = dto.getName();
        LocalDate releaseDate = dto.getReleaseDate();
        MpaRating mpaRating = mpaMapper.dtoToRating(dto.getMpa());
        Set<FilmGenre> genres = new HashSet<>(genreMapper.batchDtoToGenre(dto.getGenres()));
        return new Film(id, name, description, releaseDate, duration, mpaRating, genres);
    }

    public FilmDto filmToDto(Film film) {
        long id = film.getId();
        String description = film.getDescription();
        int duration = film.getDuration();
        String name = film.getName();
        LocalDate releaseDate = film.getReleaseDate();
        MpaRatingDto mpaDto = mpaMapper.ratingToDto(film.getRating());
        FilmGenreDto[] genreDtos = genreMapper
                .batchGenreToDto(List.copyOf(film.getGenres()))
                .toArray(new FilmGenreDto[0]);
        return new FilmDto(id, description, duration, name, releaseDate, mpaDto, genreDtos);
    }
}
