package com.alex.homework4example.service.impl;

import com.alex.homework4example.dto.RoleDTO;
import com.alex.homework4example.entity.Role;
import com.alex.homework4example.mapper.CommonMapper;
import com.alex.homework4example.repository.AbstractRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @Mock
    private AbstractRepository<Role> roleRepository;

    @Mock
    private CommonMapper<Role, RoleDTO> roleMapper;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role;
    private RoleDTO roleDTO;

    @BeforeEach
    public void setUp() {
        role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");

        roleDTO = new RoleDTO();
        roleDTO.setId(1L);
        roleDTO.setName("ROLE_USER");
    }

    @Test
    public void testCreateRole() {
        when(roleMapper.toEntity(any(RoleDTO.class))).thenReturn(role);
        when(roleRepository.create(any(Role.class))).thenReturn(role);
        when(roleMapper.toDto(any(Role.class))).thenReturn(roleDTO);

        RoleDTO createdRoleDTO = roleService.create(roleDTO);

        assertNotNull(createdRoleDTO);
        assertEquals(roleDTO.getName(), createdRoleDTO.getName());
        verify(roleMapper).toEntity(roleDTO);
        verify(roleRepository).create(role);
        verify(roleMapper).toDto(role);
    }

    @Test
    public void testFindById() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(roleMapper.toDto(role)).thenReturn(roleDTO);

        Optional<RoleDTO> foundRoleDTO = roleService.findDtoById(1L);

        assertTrue(foundRoleDTO.isPresent());
        assertEquals(roleDTO.getId(), foundRoleDTO.get().getId());
        verify(roleRepository).findById(1L);
    }

    @Test
    public void testUpdateRole() {
        when(roleMapper.toEntity(any(RoleDTO.class))).thenReturn(role);
        when(roleRepository.update(any(Role.class))).thenReturn(role);
        when(roleMapper.toDto(any(Role.class))).thenReturn(roleDTO);

        RoleDTO updatedRoleDTO = roleService.update(roleDTO);

        assertNotNull(updatedRoleDTO);
        assertEquals(roleDTO.getName(), updatedRoleDTO.getName());
        verify(roleMapper).toEntity(roleDTO);
        verify(roleRepository).update(role);
        verify(roleMapper).toDto(role);
    }

    @Test
    public void testDeleteById() {
        when(roleRepository.deleteById(1L)).thenReturn(true);

        boolean result = roleService.deleteById(1L);

        assertTrue(result);
        verify(roleRepository).deleteById(1L);
    }

    @Test
    public void testFindAll() {
        when(roleRepository.findAll()).thenReturn(List.of(role));
        when(roleMapper.toDto(any(Role.class))).thenReturn(roleDTO);

        List<RoleDTO> allRoles = roleService.findAll();

        assertEquals(1, allRoles.size());
        assertEquals(roleDTO.getName(), allRoles.get(0).getName());
        verify(roleRepository).findAll();
    }
}
