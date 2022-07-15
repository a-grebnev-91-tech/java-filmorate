package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FriendshipAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.IllegalIdException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.storage.FriendStorage;

import java.util.List;

@Service
public class UsersFriendsService {
    private final FriendStorage storage;

    @Autowired
    public UsersFriendsService(@Qualifier("friendsDBStorage") FriendStorage storage) {
        this.storage = storage;
    }

    public void addFriendship(final long initiatorId, final long friendId) {
        if (initiatorId == friendId) {
            throw new IllegalIdException("Friendship between oneself not allowed");
        }
        if (storage.unableToConfirmFriendship(initiatorId, friendId)) {
            storage.confirmFriendship(initiatorId, friendId);
        } else if (storage.unableToCreateFriendship(initiatorId, friendId)) {
            storage.addFriendship(initiatorId, friendId);
        } else {
            throw new FriendshipAlreadyExistException("Friendship between users with id " + initiatorId + " and id " +
                    friendId + " is already exist");
        }
    }

    public void removeUser(final long userId) {
        storage.removeUser(userId);
    }

    public List<Long> getFriends(final long userId) {
        return storage.getFriends(userId);
    }

    public List<Long> getMutualFriends(final long userId, final long anotherUserId) {
        return storage.getMutualFriends(userId, anotherUserId);
    }

    public void removeFriendship(final long initiatorId, final long friendId) {
        if (!storage.isFriendshipExist(initiatorId, friendId)) {
            throw new NotFoundException("Friendship between users with id " + initiatorId + " and id " +
                    friendId + " isn't exist");
        }
        if (storage.isFriendshipInitiator(initiatorId, friendId)) {
            if (storage.isFriendshipConfirmed(initiatorId, friendId)) {
                //меняем позиции и удаляем подтверждение
                storage.removeConfirmation(initiatorId, friendId);
                storage.swapInitiator(initiatorId, friendId);
            } else {
                storage.removeFriendship(initiatorId, friendId);
            }
        } else {
            storage.removeConfirmation(friendId, initiatorId);
        }
    }
}
