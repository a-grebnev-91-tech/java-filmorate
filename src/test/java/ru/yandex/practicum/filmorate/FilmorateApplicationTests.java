package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.MpaRating;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.in_db.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmorateApplicationTests {
    private final FilmDBStorage filmStorage;
    private final UserDBStorage userStorage;
    private final JdbcTemplate jdbcTemplate;

    @AfterEach
    public void clean() {
        jdbcTemplate.update("DELETE FROM users");
        jdbcTemplate.update("DELETE FROM films");
        jdbcTemplate.update("DELETE FROM film_likes");
        jdbcTemplate.update("DELETE FROM friends");
    }

    @Test
    void test1_shouldCreateUser() {
        User toCreate = getUser();
        User created = userStorage.create(toCreate);
        toCreate.setId(created.getId());
        assertEquals(toCreate, created);
    }

    @Test
    void test2_shouldObtainUserById() {
        User toCreate = getUser();
        User created = userStorage.create(toCreate);
        long id = created.getId();
        User obtained = userStorage.get(id);
        assertNotNull(obtained);
        assertTrue(obtained.getId() > 0);
    }

    @Test
    void test3_shouldObtainAllUsers() {
        User first = getUser();
        userStorage.create(first);
        User second = getAnotherUser();
        userStorage.create(second);
        List<User> users = new ArrayList<>(userStorage.getAll());
        assertFalse(users.isEmpty());
        assertEquals(2, users.size());
        assertTrue(users.get(0).getId() > 0);
        assertTrue(users.get(1).getId() > 0);
    }

    @Test
    void test4_shouldDeleteUser() {
        User toCreate = getUser();
        long id = userStorage.create(toCreate).getId();
        User deleted = userStorage.delete(id);
        assertNotNull(deleted);
        assertEquals(id, deleted.getId());
    }

    @Test
    void test5_shouldUpdateUser() {
        User user = getUser();
        user = userStorage.create(user);
        user.setName("Aleksandr");
        User updatedUser = userStorage.update(user);
        assertNotNull(updatedUser);
        assertEquals(user, updatedUser);
    }

    private User getUser() {
        return new User(0, "a@a.a", "a", "a", LocalDate.of(1991, 8, 31));
    }

    private User getAnotherUser() {
        return new User(0, "b@b.b", "b", "a", LocalDate.of(1991, 8, 31));
    }

    private Film getFilm() {
        return new Film(0, "a", "a", LocalDate.of(1991, 8, 31), 120, new MpaRating(1, "G"), null);
    }

    private Film getAnotherFilm() {
        return new Film(0, "b", "b", LocalDate.of(1991, 8, 31), 120, new MpaRating(1, "G"), null);
    }

}
