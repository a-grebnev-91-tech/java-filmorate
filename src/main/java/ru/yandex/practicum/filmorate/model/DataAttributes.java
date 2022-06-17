package ru.yandex.practicum.filmorate.model;

import java.util.HashSet;
import java.util.Set;

public abstract class DataAttributes<T> {
    private final Long dataId;
    private final Set<T> attributes;

    public DataAttributes(final long dataId) {
        this.dataId = dataId;
        attributes = new HashSet<>();
    }

    //TODO check why is always inverted
    public boolean addAttribute(final T attribute) {
        return attributes.add(attribute);
    }

    public int attributesCount() {
        return attributes.size();
    }

    public long getDataId() {
        return dataId;
    }

    public Set<T> getAttributes() {
        return Set.copyOf(attributes);
    }

    public boolean isAttributePresent(final T attribute) {
        return attributes.contains(attribute);
    }

    public boolean removeAttribute(final T attribute) {
        return attributes.remove(attribute);
    }
}
