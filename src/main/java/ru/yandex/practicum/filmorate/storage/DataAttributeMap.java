package ru.yandex.practicum.filmorate.storage;

import java.util.HashMap;
import java.util.Map;

public abstract class DataAttributeMap<D, A> {
    private final Map<D, A> storage;

    public DataAttributeMap() {
        storage = new HashMap<>();
    }

    boolean addAttribute(final A attribute) {

    }


}
