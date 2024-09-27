package com.alex.homework4example.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<Entity, DTO> {

    DTO create(DTO dto);

    Entity createEntity(Entity entity);

    Optional<DTO> findDtoById(Long id);

    Optional<Entity> findById(Long id);

    DTO update(DTO dto);

    Entity updateEntity(Entity entity);

    DTO updateEntityToDto(Entity entity);

    boolean deleteById(Long id);

    int deleteAll();

    List<DTO> findAll();


}
