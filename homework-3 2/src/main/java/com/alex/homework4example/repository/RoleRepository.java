package com.alex.homework4example.repository;

import com.alex.homework4example.entity.Role;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository extends AbstractRepository<Role> {

    public RoleRepository() {
        super(Role.class);
    }

}
