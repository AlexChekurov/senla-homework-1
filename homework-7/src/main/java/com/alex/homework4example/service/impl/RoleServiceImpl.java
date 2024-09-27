package com.alex.homework4example.service.impl;

import com.alex.homework4example.dto.RoleDTO;
import com.alex.homework4example.entity.Role;
import com.alex.homework4example.mapper.CommonMapper;
import com.alex.homework4example.repository.AbstractRepository;
import com.alex.homework4example.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends AbstractCrudService<Role, RoleDTO> implements RoleService {

    public RoleServiceImpl(AbstractRepository<Role> repository, CommonMapper<Role, RoleDTO> mapper) {
        super(repository, mapper);
    }
}
