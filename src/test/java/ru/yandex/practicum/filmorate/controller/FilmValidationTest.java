package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.yandex.practicum.filmorate.model.FilmEntry;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FilmValidationTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator;

    @BeforeEach
    public void setUpValidator() {
        validator = factory.getValidator();
    }

    @MethodSource("test1MethodSource")
    @ParameterizedTest(name = "{index}. Check valid film with {1}")
    void test1_shouldCreateValidFilm(FilmEntry film, String testDescription) {
        Set<ConstraintViolation<FilmEntry>> violations = validator.validate(film);
        assertTrue(violations.isEmpty());
    }

    Stream<Arguments> test1MethodSource() {
        FilmEntry simpleValidFilm = getValidFilm();

        FilmEntry filmWithoutDescription = getValidFilm();
        filmWithoutDescription.setDescription(null);

        FilmEntry filmWith200CharsDescriptionLength = getValidFilm();
        filmWith200CharsDescriptionLength.setDescription(getDescriptionByLength(200));

        FilmEntry filmWithReleaseDateInFuture = getValidFilm();
        filmWithReleaseDateInFuture.setReleaseDate(LocalDate.now().plusYears(100));

        FilmEntry filmWithReleaseDateOnBirthdayOfCinema = getValidFilm();
        filmWithReleaseDateOnBirthdayOfCinema.setReleaseDate(LocalDate.of(1895, 12, 28));

        FilmEntry filmWithReleaseDateIsNull = getValidFilm();
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
    void test2_shouldFailValidationInvalidMovie(FilmEntry film, String testResultDescription) {
        Set<ConstraintViolation<FilmEntry>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
        assertEquals(film, violations.iterator().next().getRootBean());
    }

    Stream<Arguments> test2MethodSource() {
        FilmEntry invalidName = getValidFilm();
        invalidName.setName("");

        FilmEntry invalidDescription = getValidFilm();
        invalidDescription.setDescription(getDescriptionByLength(201));

        FilmEntry invalidReleaseDate = getValidFilm();
        invalidReleaseDate.setReleaseDate(LocalDate.of(1895, 12, 27));

        FilmEntry durationIsZero = getValidFilm();
        durationIsZero.setDuration(0);

        FilmEntry negativeDuration = getValidFilm();
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
        return "s".repeat(length);
    }

    private FilmEntry getValidFilm() {
        return new FilmEntry(
                0,
                "Java Virtual ExMachine",
                "Epic movie about java compiler",
                LocalDate.of(1995, 5, 23),
                42
        );
    }
}