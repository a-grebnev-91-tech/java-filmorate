package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.yandex.practicum.filmorate.model.user.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserValidationTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private Validator validator;

    @BeforeAll
    void setUpValidator() {
        validator = factory.getValidator();
    }

    @MethodSource("test1MethodSource")
    @ParameterizedTest(name = "{index}. Check valid user with {1}")
    void test1_shouldCreateValidUser(User user, String testDescription) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    Stream<Arguments> test1MethodSource() {
        User simpleValidUser = getValidUser();

        User userThatWasBornYesterday = getValidUser();
        userThatWasBornYesterday.setBirthday(LocalDate.now().minusDays(1));

        User nullBirthday = getValidUser();
        nullBirthday.setBirthday(null);

        return Stream.of(
                Arguments.of(simpleValidUser, "all fields is ok"),
                Arguments.of(userThatWasBornYesterday, "birthday yesterday"),
                Arguments.of(nullBirthday, "null birthday")
        );
    }

    @MethodSource("test2MethodSource")
    @ParameterizedTest(name = "{index}. Check invalid user with {1}")
    void test2_shouldFailValidationInvalidUser(User user, String testResultDescription) {
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(user, violations.iterator().next().getRootBean());
    }

    Stream<Arguments> test2MethodSource() {
        User LoginWithSpace = getValidUser();
        LoginWithSpace.setLogin("user puser");

        User nullLogin = getValidUser();
        nullLogin.setLogin(null);

        User blankLogin = getValidUser();
        blankLogin.setLogin(" ");

        User failEmail = getValidUser();
        failEmail.setEmail("email");

        User nullEmail = getValidUser();
        nullEmail.setEmail(null);

        User emptyEmail = getValidUser();
        emptyEmail.setEmail("");

        User blankEmail = getValidUser();
        blankEmail.setEmail(" ");

        User futureBirthday = getValidUser();
        futureBirthday.setBirthday(LocalDate.now().plusDays(1));

        return Stream.of(
                Arguments.of(LoginWithSpace, "space in login"),
                Arguments.of(nullLogin, "empty login"),
                Arguments.of(blankLogin, "blank login"),
                Arguments.of(failEmail, "fail email"),
                Arguments.of(nullEmail, "null email"),
                Arguments.of(emptyEmail, "empty email"),
                Arguments.of(blankEmail, "blank email"),
                Arguments.of(futureBirthday, "birthday tomorrow")
        );
    }

    @Test
    public void test3_shouldSetUserNameByLogin() {
        String login = "login";
        String name = null;
        User user = new User(
                0,
                "email@email.em",
                login,
                name,
                LocalDate.of(1999,12,12)
        );
        assertEquals(login, user.getName(), "Name setup through constructor fail");
        user.setName("name");
        assertEquals("name", user.getName(), "Name changing fail");
        user.setName(name);
        assertEquals(login, user.getName(), "Name changing fail");
        User user1 = new User();
        user1.setLogin("login");
        assertEquals(login, user1.getName(), "Name setup through setter fail");
        user1.setName("");
        assertEquals(login, user1.getName(), "Name changing fail");
        user1.setName("name");
        assertEquals("name", user1.getName(), "Name changing fail");

    }


    private User getValidUser() {
        return new User(
                0,
                "user@puser.ru",
                "mecheniy",
                "strelok",
                LocalDate.of(1986, 4, 26)
        );
    }

}
