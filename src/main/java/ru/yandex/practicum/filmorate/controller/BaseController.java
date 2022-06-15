package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.BaseEntity;
import ru.yandex.practicum.filmorate.service.BaseService;

import java.util.Collection;

@Slf4j
public abstract class BaseController<T extends BaseEntity>{
    private final BaseService<T> service;

    public BaseController(BaseService<T> service) {
        this.service = service;
    }

    public T create(final T baseEntity) {
        T createdEntity = service.create(baseEntity);
        log.info("Create entity {}", createdEntity);
        return createdEntity;
    }

    public T delete(final long id) {
        T deletedEntity = service.delete(id);
        log.info("Delete entity {}", deletedEntity);
        return deletedEntity;
    }

    public Collection<T> findAll() {
        return service.getAll();
    }

    public T get(final long id) {
        T readEntity = service.get(id);
        log.info("Get {} entity", readEntity);
        return readEntity;
    }

    public T update(final T baseEntity) {
        T updatedEntity = service.update(baseEntity);
        log.info("Update {} entity", updatedEntity);
        return updatedEntity;
    }
}
