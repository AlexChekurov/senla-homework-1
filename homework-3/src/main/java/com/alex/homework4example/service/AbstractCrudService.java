package com.alex.homework4example.service;

import com.alex.homework4example.dao.Dao;

import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudService<Entity> implements CrudService<Entity> {

    protected abstract Dao<Long, Entity> getDao();

    protected void beforeCreate(Entity entity) {}
    protected void afterCreate(Entity entity) {}

    protected void beforeUpdate(Entity entity) {}
    protected void afterUpdate(Entity entity) {}

    protected void beforeDelete(Long id) {}
    protected void afterDelete(Long id) {}

    @Override
    public Entity create(Entity entity) {
        beforeCreate(entity);
        getDao().save(entity);
        afterCreate(entity);
        return entity;
    }

    @Override
    public Optional<Entity> findById(Long id) {
        return getDao().findById(id);
    }

    @Override
    public List<Entity> findAll() {
        return getDao().findAll();
    }

    @Override
    public Entity update(Entity entity) {
        beforeUpdate(entity);
        if (getDao().update(entity)) {
            afterUpdate(entity);
            return entity;
        }
        throw new RuntimeException("Entity not found with id: " + getEntityId(entity));
    }

    @Override
    public boolean delete(Long id) {
        beforeDelete(id);
        boolean result = getDao().delete(id);
        afterDelete(id);
        return result;
    }

    protected abstract Long getEntityId(Entity entity);
}
