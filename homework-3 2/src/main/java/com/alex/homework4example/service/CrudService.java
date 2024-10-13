package com.alex.homework4example.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<Entity, DTO> {

    DTO create(DTO dto);

    Entity createEntity(Entity entity);

    DTO findDtoById(Long id);

    Optional<Entity> findById(Long id);

    DTO update(Long id, DTO dto);

    Entity updateEntity(Entity entity);

    DTO updateEntityToDto(Entity entity);

    boolean deleteById(Long id);

    List<DTO> findAll();


}
