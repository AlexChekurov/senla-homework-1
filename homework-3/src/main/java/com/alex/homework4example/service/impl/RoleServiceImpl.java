package com.alex.homework4example.service.impl;

import com.alex.homework4example.dao.AbstractDao;
import com.alex.homework4example.entity.Role;
import com.alex.homework4example.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends AbstractCrudService<Role> implements RoleService {

    public RoleServiceImpl(AbstractDao<Role> dao) {
        super(dao);
    }
}
