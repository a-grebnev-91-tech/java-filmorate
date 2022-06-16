package ru.yandex.practicum.filmorate.model;

import java.util.Objects;

public abstract class BaseEntry {
    private long id;

    public BaseEntry() {
    }

    public BaseEntry(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntry that = (BaseEntry) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
