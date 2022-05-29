package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(FilmController.class)
class FilmValidationTest {

    private Validator validator;

    @BeforeEach
    public void setUpValidator() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @MethodSource("test1MethodSource")
    @ParameterizedTest(name = "{index}. Check valid film with {1}")
    void test1_shouldCreateValidFilm(Film film, String testDescription) {
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertTrue(violations.isEmpty());
    }

    Stream<Arguments> test1MethodSource() {
        Film simpleValidFilm = getValidFilm();

        Film filmWithoutDescription = getValidFilm();
        filmWithoutDescription.setDescription(null);

        Film filmWith200CharsDescriptionLength = getValidFilm();
        filmWith200CharsDescriptionLength.setDescription(getDescriptionByLength(200));

        Film filmWithReleaseDateInFuture = getValidFilm();
        filmWithReleaseDateInFuture.setReleaseDate(LocalDate.now().plusYears(100));

        Film filmWithReleaseDateOnBirthdayOfCinema = getValidFilm();
        filmWithReleaseDateOnBirthdayOfCinema.setReleaseDate(LocalDate.of(1895, 12, 28));

        Film filmWithReleaseDateIsNull = getValidFilm();
        filmWithReleaseDateIsNull.setReleaseDate(null);

        return Stream.of(
                Arguments.of(simpleValidFilm, "all fields is ok"),
                Arguments.of(filmWithoutDescription, "description is null"),
                Arguments.of(filmWith200CharsDescriptionLength, "description length of 200 chars"),
                Arguments.of(filmWithReleaseDateInFuture, "release date in the future"),
                Arguments.of(filmWithReleaseDateOnBirthdayOfCinema, "release date is 28.12.1895"),
                Arguments.of(filmWithReleaseDateIsNull, "release date is null")
        );
    }

    @MethodSource("test2MethodSource")
    @ParameterizedTest(name = "{index}. Check invalid film with {1}")
    void test2_shouldFailValidationInvalidMovie(Film film, String testResultDescription) {
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
        assertEquals(film, violations.iterator().next().getRootBean());
    }

    Stream<Arguments> test2MethodSource() {
        Film invalidName = getValidFilm();
        invalidName.setName("");

        Film invalidDescription = getValidFilm();
        invalidDescription.setDescription(getDescriptionByLength(201));

        Film invalidReleaseDate = getValidFilm();
        invalidReleaseDate.setReleaseDate(LocalDate.of(1895, 12, 27));

        Film durationIsZero = getValidFilm();
        durationIsZero.setDuration(0);

        Film negativeDuration = getValidFilm();
        negativeDuration.setDuration(-100);

        return Stream.of(
                Arguments.of(invalidName, "empty name"),
                Arguments.of(invalidDescription, "description size 201"),
                Arguments.of(invalidReleaseDate, "release date is 27.12.1895"),
                Arguments.of(durationIsZero, "duration is 0"),
                Arguments.of(negativeDuration, "duration is negative")
        );
    }

    private String getDescriptionByLength(int length) {
        return "s".repeat(Math.max(0, length));
    }
    private Film getValidFilm() {
        return new Film(
                0,
                "Java Virtual ExMachine",
                "Epic movie about java compiler",
                LocalDate.of(1995, 4, 23),
                42
        );
    }

}