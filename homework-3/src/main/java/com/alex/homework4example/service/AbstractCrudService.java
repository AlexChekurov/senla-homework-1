package com.alex.homework4example.service;

import com.alex.homework4example.dao.Dao;
import com.alex.homework4example.exception.DataAccessException;
import com.alex.homework4example.exception.EntityNotFoundException;

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
        try {
            beforeCreate(entity);
            getDao().save(entity);
            afterCreate(entity);
            return entity;
        } catch (Exception e) {
            throw new DataAccessException("Error creating entity", e);
        }
    }

    @Override
    public Optional<Entity> findById(Long id) {
        try {
            return getDao().findById(id);
        } catch (Exception e) {
            throw new DataAccessException("Error finding entity by id", e);
        }
    }

    @Override
    public List<Entity> findAll() {
        try {
            return getDao().findAll();
        } catch (Exception e) {
            throw new DataAccessException("Error finding all entities", e);
        }
    }

    @Override
    public Entity update(Entity entity) {
        try {
            beforeUpdate(entity);
            if (getDao().update(entity)) {
                afterUpdate(entity);
                return entity;
            }
            throw new EntityNotFoundException("Entity not found");
        } catch (Exception e) {
            throw new DataAccessException("Error updating entity", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            beforeDelete(id);
            boolean result = getDao().delete(id);
            afterDelete(id);
            return result;
        } catch (Exception e) {
            throw new DataAccessException("Error deleting entity", e);
        }
    }
}
