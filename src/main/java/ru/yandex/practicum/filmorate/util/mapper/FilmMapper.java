package ru.yandex.practicum.filmorate.util.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.dto.repo.FilmRepoDto;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;
import ru.yandex.practicum.filmorate.model.film.MpaRating;
import ru.yandex.practicum.filmorate.model.dto.web.FilmWebDto;
import ru.yandex.practicum.filmorate.model.dto.web.GenreWebDto;
import ru.yandex.practicum.filmorate.model.dto.web.MpaRatingWebDto;
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

    public Film webDtoToFilm(FilmWebDto dto) {
        long id = dto.getId();
        String description = dto.getDescription();
        int duration = dto.getDuration();
        String name = dto.getName();
        LocalDate releaseDate = dto.getReleaseDate();
        MpaRating mpaRating = mpaMapper.dtoToRating(dto.getMpa());
        Set<FilmGenre> genres = new HashSet<>(genreMapper.batchDtoToGenre(dto.getGenres()));
        return new Film(id, name, description, releaseDate, duration, mpaRating, genres);
    }

    public FilmWebDto filmToWebDto(Film film) {
        long id = film.getId();
        String description = film.getDescription();
        int duration = film.getDuration();
        String name = film.getName();
        LocalDate releaseDate = film.getReleaseDate();
        MpaRatingWebDto mpaDto = mpaMapper.ratingToDto(film.getRating());
        GenreWebDto[] genreDtos = genreMapper
                .batchGenreToDto(List.copyOf(film.getGenres()))
                .toArray(new GenreWebDto[0]);
        return new FilmWebDto(id, description, duration, name, releaseDate, mpaDto, genreDtos);
    }

    public FilmRepoDto filmToRepoDto(Film film) {
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

    public Film repoDtoToFilm(FilmRepoDto filmRepoDto, Set<FilmGenre> genres) {
        return new Film(
                filmRepoDto.getId(),
                filmRepoDto.getName(),
                filmRepoDto.getDescription(),
                filmRepoDto.getReleaseDate(),
                filmRepoDto.getLikesCount(),
                filmRepoDto.getDuration(),
                MpaRating.getById(filmRepoDto.getMpaRatingId()),
                genres
        );
    }
}
