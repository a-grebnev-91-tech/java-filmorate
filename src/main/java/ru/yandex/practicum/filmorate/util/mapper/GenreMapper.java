package ru.yandex.practicum.filmorate.util.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.dto.FilmGenreDto;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class GenreMapper {

    public FilmGenre dtoToGenre(FilmGenreDto dto) {
        return FilmGenre.getById(dto.getId());
    }

    public FilmGenreDto genreToDto(FilmGenre genre) {
        return new FilmGenreDto(genre.getId(), genre.getTitle());
    }

    public List<FilmGenre> batchDtoToGenre(List<FilmGenreDto> dtos) {
        List<FilmGenre> genres;
        if (dtos != null) {
            genres = dtos.stream().map(this::dtoToGenre).collect(Collectors.toList());
        } else {
            genres = Collections.emptyList();
        }
        return genres;
    }

    public List<FilmGenre> batchDtoToGenre(FilmGenreDto[] dtos) {
        List<FilmGenreDto> list;
        if (dtos == null) {
            list = null;
        } else {
            list = Arrays.asList(dtos);
        }
        return batchDtoToGenre(list);
    }

    public List<FilmGenreDto> batchGenreToDto(List<FilmGenre> genres) {
        List<FilmGenreDto> dtos;
        if (genres != null) {
            dtos = genres.stream().map(this::genreToDto).collect(Collectors.toList());
        } else {
            dtos = Collections.emptyList();
        }
        return dtos;
    }
}
