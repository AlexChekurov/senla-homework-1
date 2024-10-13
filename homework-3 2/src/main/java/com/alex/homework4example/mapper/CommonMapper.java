package com.alex.homework4example.mapper;

public interface CommonMapper<E, D> {

    E toEntity(D dto);

    D toDto(E entity);
}
