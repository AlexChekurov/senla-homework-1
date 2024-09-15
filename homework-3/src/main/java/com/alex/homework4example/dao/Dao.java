package com.alex.homework4example.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<Id, Entity> {
    Entity save(Entity entity);
    Optional<Entity> findById(Id id);
    List<Entity> findAll();
    boolean update(Entity entity);
    boolean delete(Id id);
}
