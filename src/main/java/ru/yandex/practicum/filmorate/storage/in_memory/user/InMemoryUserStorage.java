package ru.yandex.practicum.filmorate.storage.in_memory.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.in_memory.InMemoryDataStorage;

@Component
public class InMemoryUserStorage extends InMemoryDataStorage<User> {
}
