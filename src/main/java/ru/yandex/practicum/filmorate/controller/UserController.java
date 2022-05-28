package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IllegalIdException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private long id = 1;
    private final Map<Long, User> users = new HashMap<>();

    @PostMapping
    public User create(@RequestBody @Valid User user) {
//        if (user.getId() != 0) {
//            log.warn("Attempt to create user with assigned id");
//            throw new IllegalIdException("Cannot create user with assigned id");
//        }
        long currentId = generateId();
        user.setId(currentId);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.info("Add user {}", user);
        users.put(currentId, user);
        return user;
    }

    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        long id = user.getId();
        if (users.containsKey(id)) {
            log.info("Update user {}", user);
            users.put(id, user);
            return user;
        }
        log.warn("Attempt to update non-existing user");
        throw new IllegalIdException("Id " + id + " isn't exist");
    }

    private long generateId() {
        return id++;
    }
}
