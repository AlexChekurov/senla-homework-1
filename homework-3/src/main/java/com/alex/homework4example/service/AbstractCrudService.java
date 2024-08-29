package com.alex.homework4example.service;

import com.alex.homework4example.dao.Dao;
import com.alex.homework4example.mapper.Mapper;
import com.alex.homework4example.service.CrudService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractCrudService<Entity, DTO, ID> implements CrudService<DTO, ID> {

    protected abstract Dao<ID, Entity> getDao();

    protected abstract Mapper<Entity, DTO> getMapper();

    @Override
    public DTO create(DTO dto) {
        Entity entity = getMapper().toEntity(dto);
        getDao().save(entity);
        return getMapper().toDto(entity);
    }

    @Override
    public Optional<DTO> findById(ID id) {
        return getDao().findById(id).map(getMapper()::toDto);
    }

    @Override
    public List<DTO> findAll() {
        return getDao().findAll().stream()
                .map(getMapper()::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DTO update(DTO dto) {
        Entity entity = getMapper().toEntity(dto);
        if (getDao().update(entity)) {
            return getMapper().toDto(entity);
        }
        throw new RuntimeException("Entity not found with id: " + getEntityId(entity));
    }

    @Override
    public boolean delete(ID id) {
        return getDao().delete(id);
    }

    protected abstract ID getEntityId(Entity entity);
}
