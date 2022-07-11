package ru.yandex.practicum.filmorate.storage.in_memory.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.user.UsersFriends;
import ru.yandex.practicum.filmorate.storage.in_memory.InMemoryDataAttributesStorage;

@Component("inMemoryFriendsStorage")
public class InMemoryUsersFriendsStorage extends InMemoryDataAttributesStorage<UsersFriends> {
}
