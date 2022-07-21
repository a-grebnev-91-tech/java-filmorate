package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.film.FilmDirector;

import java.util.List;
import java.util.Set;

public interface DirectorStorage {
    void addDirectorToFilm(long filmId, long directorId);

    long createDirector(FilmDirector director);

    FilmDirector deleteDirector(long id);

    void deleteFilmFromFilmsDirectors(long deletedId);

    List<FilmDirector> getAllDirectors();

    FilmDirector getDirector(long id);

    List<FilmDirector> getDirectorsByFilm(long filmId);

    List<Long> getFilmsByDirectors(long directorId);

    List<FilmDirector> getSomeDirectors(List<Long> directorsIds);

    boolean updateDirector(FilmDirector director);

    void updateFilmDirector(long filmId, List<Long> directorsIds);

    void deleteDirectorFromFilmsDirectors(long directorIds);
}
