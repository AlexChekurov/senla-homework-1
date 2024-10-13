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
class RoleServiceImplTest {

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
    void testCreateRole() {
        //given
        when(roleMapper.toEntity(any(RoleDTO.class))).thenReturn(role);
        when(roleRepository.create(any(Role.class))).thenReturn(role);
        when(roleMapper.toDto(any(Role.class))).thenReturn(roleDTO);

        //when
        RoleDTO createdRoleDTO = roleService.create(roleDTO);

        //then
        assertNotNull(createdRoleDTO);
        assertEquals(roleDTO.getName(), createdRoleDTO.getName());
        verify(roleMapper).toEntity(roleDTO);
        verify(roleRepository).create(role);
        verify(roleMapper).toDto(role);
    }

    @Test
    void testFindById() {
        //given
        when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(roleMapper.toDto(role)).thenReturn(roleDTO);

        //when
        RoleDTO foundRoleDTO = roleService.findDtoById(1L);

        //then
        assertEquals(roleDTO.getId(), foundRoleDTO.getId());
        verify(roleRepository).findById(1L);
    }

    @Test
    void testUpdateRole() {
        //given
        when(roleRepository.findById(role.getId()))
                .thenReturn(Optional.ofNullable(role));
        when(roleRepository.update(any(Role.class))).thenReturn(role);
        when(roleMapper.toDto(any(Role.class))).thenReturn(roleDTO);

        //when
        RoleDTO updatedRoleDTO = roleService.update(role.getId(), roleDTO);

        //then
        assertNotNull(updatedRoleDTO);
        assertEquals(roleDTO.getName(), updatedRoleDTO.getName());
        verify(roleRepository).update(role);
        verify(roleMapper).toDto(role);
    }

    @Test
    void testDeleteById() {
        //given
        when(roleRepository.deleteById(1L)).thenReturn(true);

        //when
        boolean result = roleService.deleteById(1L);

        //then
        assertTrue(result);
        verify(roleRepository).deleteById(1L);
    }

    @Test
    void testFindAll() {
        //given
        when(roleRepository.findAll()).thenReturn(List.of(role));
        when(roleMapper.toDto(any(Role.class))).thenReturn(roleDTO);

        //when
        List<RoleDTO> allRoles = roleService.findAll();

        //then
        assertEquals(1, allRoles.size());
        assertEquals(roleDTO.getName(), allRoles.get(0).getName());
        verify(roleRepository).findAll();
    }
}
