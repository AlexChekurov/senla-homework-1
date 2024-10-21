package com.alex.homework4example.controller;

import com.alex.homework4example.dto.RoleDTO;
import com.alex.homework4example.exception.EntityNotFoundException;
import com.alex.homework4example.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private RoleService roleService;

    @GetMapping
    public List<RoleDTO> getAllRoles() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public RoleDTO getRoleById(@PathVariable("id") Long id) {
        return roleService.findDtoById(id);
    }

    @PostMapping
    public RoleDTO createRole(@RequestBody RoleDTO role) {
        return roleService.create(role);
    }

    @PutMapping("/{id}")
    public RoleDTO updateRole(@PathVariable("id") Long id, @RequestBody RoleDTO roleDetails) {
        return roleService.update(id, roleDetails);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteRole(@PathVariable("id") Long id) {
        roleService.deleteById(id);
    }

}
