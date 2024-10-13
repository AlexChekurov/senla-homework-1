package com.alex.homework4example.service.impl;

import com.alex.homework4example.dto.RoleDTO;
import com.alex.homework4example.entity.Role;
import com.alex.homework4example.exception.EntityNotFoundException;
import com.alex.homework4example.mapper.CommonMapper;
import com.alex.homework4example.repository.AbstractRepository;
import com.alex.homework4example.service.RoleService;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl extends AbstractCrudService<Role, RoleDTO> implements RoleService {

    public RoleServiceImpl(AbstractRepository<Role> repository, CommonMapper<Role, RoleDTO> mapper) {
        super(repository, mapper);
    }

    @Override
    public RoleDTO update(Long id, RoleDTO roleDetails) {
        return findById(id)
                .map(role -> {
                    role.setName(roleDetails.getName());
                    return updateEntityToDto(role);
                })
                .orElseThrow(() -> new EntityNotFoundException("Can't update role with id :" + id));
    }

}
