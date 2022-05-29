package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmControllerTest {

    @Autowired
    private FilmController controller;
    private final LocalDate validReleaseDate = LocalDate.of(1995, 4, 23);

    @Test
    void test1_shouldCreateValidFilm() {
        Film film = getValidFilm();
        Film createdFilm = controller.create(film);
        assertTrue(controller.findAll().contains(createdFilm));
    }

    @Test
    void test2_shouldIgnoreFilmWithEmptyName() {
        Film film = getValidFilm();
        film.setDuration(-10);
        controller.create(film);
        System.out.println(controller.findAll());
        assertTrue(controller.findAll().isEmpty());
    }

    private Film getValidFilm() {
        return new Film(
                0,
                "Java Virtual ExMachine",
                "Epic movie about java compiler",
                validReleaseDate,
                42
        );
    }
}