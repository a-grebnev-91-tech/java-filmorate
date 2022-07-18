package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.dto.repo.FilmRepoDto;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {
    long create(FilmRepoDto film);

    long delete(long id);

    FilmRepoDto get(long id);

    List<FilmRepoDto> getAll();

    List<FilmRepoDto> getSome(Collection<Long> ids);

    boolean isExist(long id);

    boolean update(FilmRepoDto film);

    boolean updateLikesCount(long id);
}
