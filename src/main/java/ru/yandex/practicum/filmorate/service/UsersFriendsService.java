package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.UsersFriendsData;
import ru.yandex.practicum.filmorate.storage.InMemoryUsersFriendsStorage;

@Service
public class UsersFriendsService extends BaseService<UsersFriendsData> {

    @Autowired
    public UsersFriendsService(InMemoryUsersFriendsStorage storage) {
        super(storage);
    }

    public void addFriend(final long userId, final long friendId) {
        UsersFriendsData usersFriends;
        try {
            usersFriends = get(userId);
        } catch (NotFoundException ex) {
            usersFriends = new UsersFriendsData(userId);
        }
        usersFriends.addFriend(friendId);
        create(usersFriends);
    }

    public void removeFriend(final long userId, final long friendId) {
        get(userId).removeFriend(friendId);
    }
}
