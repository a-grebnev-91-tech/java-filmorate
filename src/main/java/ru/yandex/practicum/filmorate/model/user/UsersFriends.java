package ru.yandex.practicum.filmorate.model.user;

import ru.yandex.practicum.filmorate.model.DataAttributes;

import java.util.Set;

public class UsersFriends extends DataAttributes<Long> implements Cloneable {

    public UsersFriends(long userId) {
        super(userId);
    }

    public boolean addFriend(final long friendId) {
        return super.addAttribute(friendId);
    }

    public long getUserId() {
        return super.getDataId();
    }

    public Set<Long> getFriends() {
        return super.getAttributes();
    }

    public int friendsCount() {
        return super.attributesCount();
    }

    public boolean isFriend(final long friendId) {
        return super.isAttributePresent(friendId);
    }

    public boolean removeFriend(final long friendId) {
        return super.removeAttribute(friendId);
    }

    @Override
    public UsersFriends clone() {
        UsersFriends friends = new UsersFriends(this.getUserId());
        for (Long friendId : getFriends()) {
            friends.addFriend(friendId);
        }
        return friends;
    }
}
