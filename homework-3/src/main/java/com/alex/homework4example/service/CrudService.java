package com.alex.homework4example.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<Entity> {
    Entity create(Entity entity);
    Optional<Entity> findById(Long id);
    List<Entity> findAll();
    Entity update(Entity entity);
    boolean delete(Long id);
}
