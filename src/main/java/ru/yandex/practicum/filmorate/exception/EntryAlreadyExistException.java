package ru.yandex.practicum.filmorate.exception;

public class EntryAlreadyExistException extends RuntimeException {
    public EntryAlreadyExistException(String message) {
        super(message);
    }
}
