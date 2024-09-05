package com.alex.homework4example.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<K, E> {
    E save(E e);
    Optional<E> findById(K id);
    List<E> findAll();
    boolean update(E e);
    boolean delete(K id);
}
