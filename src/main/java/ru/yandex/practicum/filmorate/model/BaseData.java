package ru.yandex.practicum.filmorate.model;

import java.util.Objects;

public abstract class BaseData {
    private long id;

    public BaseData() {
    }

    public BaseData(long id) {
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
        BaseData that = (BaseData) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
