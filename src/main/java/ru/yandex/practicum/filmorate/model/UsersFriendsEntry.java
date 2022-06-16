package ru.yandex.practicum.filmorate.model;

import java.util.HashSet;
import java.util.Set;

public class UsersFriendsEntry extends BaseEntry{
    private final Set<Long> friends;

    public UsersFriendsEntry(final long userId) {
        super(userId);
        friends = new HashSet<>();
    }

    public void addFriend(final long friendId) {
        friends.add(friendId);
    }

    public long getUserId() {
        return super.getId();
    }

    public boolean isFriend(final long friendId) {
        return friends.contains(friendId);
    }

    public void removeFriend(final long friendId) {
        friends.remove(friendId);
    }
}
