package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.UserData;
import ru.yandex.practicum.filmorate.storage.Storage;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService extends BaseService<UserData> {

    @Autowired
    public UserService(Storage<UserData> storage) {
        super(storage);
    }

    public void addFriend(final long userId, final long friendId) {
        UserData user = get(userId);
        UserData friend = get(friendId);
        user.addFriend(friendId);
        friend.addFriend(userId);
    }

    //todo check
    public List<UserData> getFriends(final long id) {
        Set<Long> friendsIds = get(id).friends();
        return getSome(friendsIds);
    }

    //TODO check
    public List<UserData> getMutualFriends(final long userId, final long friendId) {
        Set<Long> userFriendsIds = get(userId).friends();
        Set<Long> friendFriendsIds = get(friendId).friends();
        Set<Long> mutualFriendsIds = userFriendsIds
                .stream()
                .filter(friendFriendsIds::contains)
                .collect(Collectors.toSet());
        return getSome(mutualFriendsIds);
    }

    public void removeFriend(final long userId, final long friendId) {
        UserData user = get(userId);
        UserData friend = get(friendId);
        user.removeFriend(friendId);
        friend.removeFriend(userId);
    }
}
