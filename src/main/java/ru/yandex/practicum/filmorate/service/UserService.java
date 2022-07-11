package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.DataStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UsersFriendsService friendsService;
    private final DataStorage<User> usersStorage;

    @Autowired
    public UserService(@Qualifier("userDBStorage") DataStorage<User> dataStorage, UsersFriendsService friendsService) {
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
        friendsService.addUser(user.getId());
        return user;
    }

    public User deleteUser(final long id) {
        User deletedUser = usersStorage.delete(id);
        friendsService.deleteUser(id);
        return deletedUser;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(usersStorage.getAll());
    }

    public List<User> getFriends(final long id) {
        Set<Long> friendsIds = friendsService.getFriends(id);
        return new ArrayList<>(usersStorage.getSome(friendsIds));
    }

    public List<User> getMutualFriends(final long userId, final long anotherUserId) {
        return new ArrayList<>(usersStorage.getSome(friendsService.getMutualFriends(userId, anotherUserId)));
    }

    public User getUser(long id) {
        return usersStorage.get(id);
    }

    public boolean isUserExist(final long userId) {
        return usersStorage.isExist(userId);
    }

    public void removeFriendship(final long userId, final long anotherUserId) {
        friendsService.removeFriendship(userId, anotherUserId);
    }

    public User updateUser(final User user) {
        return usersStorage.update(user);
    }
}
