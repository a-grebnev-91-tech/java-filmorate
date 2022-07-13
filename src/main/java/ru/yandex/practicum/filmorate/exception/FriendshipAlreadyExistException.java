package ru.yandex.practicum.filmorate.exception;

public class FriendshipAlreadyExistException extends RuntimeException{
    public FriendshipAlreadyExistException(String message) {
        super(message);
    }
}
