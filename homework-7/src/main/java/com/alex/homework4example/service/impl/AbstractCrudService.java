package com.alex.homework4example.service.impl;

import com.alex.homework4example.mapper.CommonMapper;
import com.alex.homework4example.repository.AbstractRepository;
import com.alex.homework4example.service.CrudService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public abstract class AbstractCrudService<E, D> implements CrudService<E, D> {

    AbstractRepository<E> repository;

    CommonMapper<E, D> mapper;

    @Override
    public D create(D dto) {
        var entity = mapper.toEntity(dto);
        entity = createEntity(entity);
        return mapper.toDto(entity);
    }

    @Override
    public E createEntity(E entity) {
        return repository.create(entity);
    }

    @Override
    public Optional<E> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<D> findDtoById(Long id) {
        return findById(id)
                .map(mapper::toDto);
    }

    @Override
    public D update(D dto) {
        var entity = mapper.toEntity(dto);
        return mapper.toDto(updateEntity(entity));
    }

    @Override
    public E updateEntity(E entity) {
        return repository.update(entity);
    }

    @Override
    public D updateEntityToDto(E entity) {
        return mapper.toDto(updateEntity(entity));
    }

    @Override
    public boolean deleteById(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public int deleteAll() {
        return repository.deleteAll();
    }

    @Override

    public List<D> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }
}
