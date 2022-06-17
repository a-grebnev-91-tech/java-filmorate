package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.user.UsersFriends;
import ru.yandex.practicum.filmorate.storage.InMemoryDataAttributesStorage;

@Component
public class InMemoryUsersFriendsStorage extends InMemoryDataAttributesStorage<UsersFriends> {
}
