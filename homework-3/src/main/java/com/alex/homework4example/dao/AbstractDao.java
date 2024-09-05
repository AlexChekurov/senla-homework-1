package com.alex.homework4example.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<K, E> implements Dao<K, E> {
    protected final List<E> storage = new ArrayList<>();

    @Override
    public E save(E e) {
        if (getId(e) == null) {
            setId(e, generateId());
            storage.add(e);
        } else {
            update(e);
        }
        return e;
    }

    @Override
    public Optional<E> findById(K id) {
        return storage.stream()
                .filter(e -> id.equals(getId(e)))
                .findFirst();
    }

    @Override
    public List<E> findAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public boolean update(E e) {
        Optional<E> existingEntity = findById(getId(e));
        existingEntity.ifPresent(entity -> {
            int index = storage.indexOf(entity);
            storage.set(index, e);
        });
        return existingEntity.isPresent();
    }

    @Override
    public boolean delete(K id) {
        return storage.removeIf(e -> id.equals(getId(e)));
    }

    protected abstract K getId(E e);
    protected abstract void setId(E e, K id);

    protected abstract K generateId();
}
