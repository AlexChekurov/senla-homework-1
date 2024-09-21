package com.alex.homework4example.dao;

import com.alex.homework4example.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDao extends AbstractDao<Role> {

    public RoleDao() {
        super(Role.class);
    }

}
