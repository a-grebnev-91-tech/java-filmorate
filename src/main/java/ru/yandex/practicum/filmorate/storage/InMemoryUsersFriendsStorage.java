package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.EntryAlreadyExistException;
import ru.yandex.practicum.filmorate.model.UsersFriendsData;

@Component
public class InMemoryUsersFriendsStorage extends InMemoryStorage<UsersFriendsData> {

    @Override
    public UsersFriendsData create(UsersFriendsData friendsData) {
        if (isExist(friendsData.getUserId())) {
            throw new EntryAlreadyExistException("Cannot create users friends entry because it already exist");
        }
        super.storage.put(friendsData.getUserId(), friendsData);
        return friendsData;
    }
}
