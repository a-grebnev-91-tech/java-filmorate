package ru.yandex.practicum.filmorate.util.mapper;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.dto.web.GenreWebDto;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class GenreMapper {

    public FilmGenre dtoToGenre(GenreWebDto dto) {
        return FilmGenre.getById(dto.getId());
    }

    public GenreWebDto genreToDto(FilmGenre genre) {
        return new GenreWebDto(genre.getId(), genre.getTitle());
    }

    public List<FilmGenre> batchDtoToGenre(List<GenreWebDto> dtos) {
        List<FilmGenre> genres;
        if (dtos != null) {
            genres = dtos.stream().map(this::dtoToGenre).collect(Collectors.toList());
        } else {
            genres = Collections.emptyList();
        }
        return genres;
    }

    public List<FilmGenre> batchDtoToGenre(GenreWebDto[] dtos) {
        List<GenreWebDto> list;
        if (dtos == null) {
            list = null;
        } else {
            list = Arrays.asList(dtos);
        }
        return batchDtoToGenre(list);
    }

    public List<GenreWebDto> batchGenreToDto(List<FilmGenre> genres) {
        List<GenreWebDto> dtos;
        if (genres != null) {
            dtos = genres.stream().map(this::genreToDto).collect(Collectors.toList());
            sort(dtos);
        } else {
            dtos = Collections.emptyList();
        }
        return dtos;
    }

    private void sort(List<GenreWebDto> list) {
        list.sort(Comparator.comparingInt(GenreWebDto::getId));
    }
}
