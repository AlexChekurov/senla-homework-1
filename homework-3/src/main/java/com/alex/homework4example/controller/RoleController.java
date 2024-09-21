package com.alex.homework4example.controller;

import com.alex.homework4example.entity.Role;
import com.alex.homework4example.enumeration.RoleName;
import com.alex.homework4example.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class RoleController {
    private final RoleService roleService;

    public List<Role> createAllRoles() {
        roleService.deleteAll();
        return Arrays.stream(RoleName.values())
                .map(role -> Role.builder().name(role).build())
                .map(roleService::create)
                .toList();
    }
}
