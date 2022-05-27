package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    private final Set<User> users = new HashSet<>();

    @PostMapping
    public User create(@RequestBody User user) {
        if (users.add(user))
            return user;
        else
            throw new ValidationException(""); //TODO fix this
    }

    @GetMapping
    public Set<User> findAll() {
        return users;
    }

    @PutMapping
    public User update(@RequestBody User user) {
        if (users.add(user))
            return user;
        else
            throw new ValidationException(""); //TODO fix this
    }
}
