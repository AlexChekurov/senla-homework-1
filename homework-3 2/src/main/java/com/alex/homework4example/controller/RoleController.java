package com.alex.homework4example.controller;

import com.alex.homework4example.dto.RoleDTO;
import com.alex.homework4example.exception.EntityNotFoundException;
import com.alex.homework4example.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class RoleController {

    private RoleService roleService;

    public List<RoleDTO> getAllRoles() {
        return roleService.findAll();
    }

    public RoleDTO getRoleById(Long id) {
        return roleService.findDtoById(id);
    }

    public RoleDTO createRole(RoleDTO role) {
        return roleService.create(role);
    }

    public RoleDTO updateRole(Long id, RoleDTO roleDetails) {
        return roleService.update(id, roleDetails);
    }

    public void deleteRole(Long id) {
        roleService.deleteById(id);
    }
}
