package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.InMemoryDataStorage;

@Component
public class InMemoryUserStorage extends InMemoryDataStorage<User> {
}
