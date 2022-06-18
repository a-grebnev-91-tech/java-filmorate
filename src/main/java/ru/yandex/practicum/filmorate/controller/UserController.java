package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.IllegalIdException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriendship(
            @PathVariable("id") final long userId,
            @PathVariable("friendId") final long anotherUserId
    ) {
        service.addFriendship(userId, anotherUserId);
        log.info("User with id = {} add user with id = {} to friends", userId, anotherUserId);
    }

    @PostMapping
    public User create(@RequestBody @Valid User user) {
        if (user.getId() != 0) {
            log.warn("Attempt to create user with assigned id");
            throw new IllegalIdException("Cannot create user with assigned id");
        }
        User created = service.createUser(user);
        log.info("Add user {}", created);
        return created;
    }

    @DeleteMapping("/{id}")
    public User delete(@PathVariable final long id) {
        User deletedUser = service.deleteUser(id);
        log.info("Delete film {}", deletedUser);
        return deletedUser;
    }

    @GetMapping
    public Collection<User> findAll() {
        List<User> users = service.getAllUsers();
        log.info("Get all users");
        return users;
    }

    @GetMapping("/{id}")
    public User get(@PathVariable long id) {
        User readUser = service.getUser(id);
        log.info("Get {}", readUser);
        return readUser;
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable final long id) {
        List<User> friends = service.getFriends(id);
        log.info("Get friends from user with id = {}", id);
        return friends;
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(
            @PathVariable final long id,
            @PathVariable final long otherId
    ) {
        List<User> friends = service.getMutualFriends(id, otherId);
        log.info("Get friends from user with id = {}", id);
        return friends;
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriendship(
            @PathVariable("id") final long userId,
            @PathVariable("friendId") final long anotherUserId
    ) {
        service.removeFriendship(userId, anotherUserId);
        log.info("Friendship between users with ids {} and {} has been removed", userId, anotherUserId);
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        User updatedUser = service.updateUser(user);
        log.info("Update user {}", updatedUser);
        return updatedUser;
    }
}
