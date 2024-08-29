package com.alex.homework4example.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<DTO, ID> {
    DTO create(DTO dto);
    Optional<DTO> findById(ID id);
    List<DTO> findAll();
    DTO update(DTO dto);
    boolean delete(ID id);
}
