package com.alex.homework4example.service.impl;

import com.alex.homework4example.dto.UserDTO;
import com.alex.homework4example.entity.User;
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
class UserServiceImplTest {

    @Mock
    private AbstractRepository<User> userRepository;

    @Mock
    private CommonMapper<User, UserDTO> userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("password");

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("testUser");
        userDTO.setPassword("password");
    }

    @Test
    void testCreateUser() {
        //given
        when(userMapper.toEntity(any(UserDTO.class))).thenReturn(user);
        when(userRepository.create(any(User.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);

        //when
        UserDTO createdUserDTO = userService.create(userDTO);

        //then
        assertNotNull(createdUserDTO);
        assertEquals(userDTO.getUsername(), createdUserDTO.getUsername());
        verify(userMapper).toEntity(userDTO);
        verify(userRepository).create(user);
        verify(userMapper).toDto(user);
    }

    @Test
    void testFindById() {
        //given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDTO);

        //when
        UserDTO foundUserDTO = userService.findDtoById(1L);

        //then
        assertEquals(userDTO.getId(), foundUserDTO.getId());
        verify(userRepository).findById(1L);
    }

    @Test
    void testUpdateUser() {
        //given
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
        when(userRepository.update(any(User.class))).thenReturn(user);
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);

        //when
        UserDTO updatedUserDTO = userService.update(user.getId(), userDTO);

        //then
        assertNotNull(updatedUserDTO);
        assertEquals(userDTO.getUsername(), updatedUserDTO.getUsername());
        verify(userRepository).update(user);
        verify(userMapper).toDto(user);
    }

    @Test
    void testDeleteById() {
        //given
        when(userRepository.deleteById(1L)).thenReturn(true);

        //when
        boolean result = userService.deleteById(1L);

        //then
        assertTrue(result);
        verify(userRepository).deleteById(1L);
    }

    @Test
    void testFindAll() {
        //given
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toDto(any(User.class))).thenReturn(userDTO);

        //when
        List<UserDTO> allUsers = userService.findAll();

        //then
        assertEquals(1, allUsers.size());
        assertEquals(userDTO.getUsername(), allUsers.get(0).getUsername());
        verify(userRepository).findAll();
    }

}
