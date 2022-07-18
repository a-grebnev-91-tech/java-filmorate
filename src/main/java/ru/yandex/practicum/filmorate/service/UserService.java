package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    private final UsersFriendsService friendsService;
    private final UserStorage usersStorage;

    @Autowired
    public UserService(@Qualifier("userDBStorage") UserStorage dataStorage, UsersFriendsService friendsService) {
        this.usersStorage = dataStorage;
        this.friendsService = friendsService;
    }

    public void addFriendship(final long userId, final long anotherUserId) {
        if (isUserExist(userId) && isUserExist(anotherUserId)) {
            friendsService.addFriendship(userId, anotherUserId);
        } else if (isUserExist(userId)) {
            throw new NotFoundException("User with id " + anotherUserId + " isn't exist");
        } else {
            throw new NotFoundException("User with id " + userId + " isn't exist");
        }
    }

    public User createUser(User user) {
        user = usersStorage.create(user);
        return user;
    }

    public User deleteUser(final long id) {
        User deletedUser = usersStorage.delete(id);
        friendsService.removeUser(id);
        return deletedUser;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(usersStorage.getAll());
    }

    public List<User> getFriends(final long id) {
        List<Long> friendsIds = friendsService.getFriends(id);
        return getFriendsByIds(friendsIds);
    }

    public List<User> getMutualFriends(final long userId, final long anotherUserId) {
        List<Long> friendsIds = friendsService.getMutualFriends(userId, anotherUserId);
        return getFriendsByIds(friendsIds);

    }

    public User getUser(long id) {
        return usersStorage.get(id);
    }

    public boolean isUserExist(final long userId) {
        return usersStorage.isExist(userId);
    }

    public void removeFriendship(final long initiatorId, final long friendId) {
        friendsService.removeFriendship(initiatorId, friendId);
    }

    public User updateUser(final User user) {
        return usersStorage.update(user);
    }

    private List<User> getFriendsByIds(List<Long> friendsIds) {
        if (friendsIds.size() > 1) {
            return List.copyOf(usersStorage.getSome(friendsIds));
        } else if (friendsIds.size() == 1) {
            return List.of(usersStorage.get(friendsIds.get(0)));
        } else {
            return Collections.emptyList();
        }
    }
}
