package com.alex.homework4example.service.impl;

import com.alex.homework4example.dao.AbstractDao;
import com.alex.homework4example.service.CrudService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public abstract class AbstractCrudService<T> implements CrudService<T> {

    AbstractDao<T> dao;

    @Override
    public T create(T t) {
        return dao.create(t);
    }

    @Override
    public Optional<T> findById(Long id) {
        return dao.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        return dao.deleteById(id);
    }

    @Override
    public int deleteAll() {
        return dao.deleteAll();
    }

    @Override
    public T update(T role) {
        return null;
    }

    @Override
    public List<T> findAll() {
        return dao.findAll();
    }
}
