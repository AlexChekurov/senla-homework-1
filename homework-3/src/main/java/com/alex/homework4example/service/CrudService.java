package com.alex.homework4example.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<Entity> {

    Entity create(Entity entity);

    Optional<Entity> findById(Long id);

    Entity update(Entity entity);

    boolean deleteById(Long id);

    int deleteAll();

    List<Entity> findAll();


}
