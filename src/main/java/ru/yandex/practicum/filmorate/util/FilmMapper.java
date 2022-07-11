package ru.yandex.practicum.filmorate.util;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.MpaRating;
import ru.yandex.practicum.filmorate.model.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.dto.FilmGenreDto;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class FilmMapper {

    public Film FilmDtoToFilm(FilmDto dto) {
        long id = dto.getId();
        String description = dto.getDescription();
        int duration = dto.getDuration();
        String name = dto.getName();
        LocalDate releaseDate = dto.getReleaseDate();
        MpaRating mpaRating = MpaRating.getByOrdinal(dto.getMpa().getId());
        Set<FilmGenre> genres = new HashSet<>();
        FilmGenreDto[] genreDtos = dto.getGenres();
        for(FilmGenreDto genreDto : genreDtos) {
            genres.add(FilmGenre.getByOrdinal(genreDto.getId()));
        }
        return new Film(id, name, description, releaseDate, duration, mpaRating, genres);
    }
}
