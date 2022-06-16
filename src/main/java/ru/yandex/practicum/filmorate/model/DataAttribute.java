package ru.yandex.practicum.filmorate.model;

import java.util.HashSet;
import java.util.Set;

public abstract class DataAttribute<D, A> {
    private D data;
    private Set<A> attributes;

    public DataAttribute(final D data) {
        this.data = data;
        attributes = new HashSet<>();
    }

    public boolean addAttribute(final A attribute) {
        return attributes.add(attribute);
    }

    public int attributesCount() {
        return attributes.size();
    }

    public D getData() {
        return data;
    }

    public Set<A> getAttributes() {
        return Set.copyOf(attributes);
    }

    public boolean isAttributePresent(final A attribute) {
        return attributes.contains(attribute);
    }

    public boolean removeAttribute(final A attribute) {
        return attributes.remove(attribute);
    }
}
