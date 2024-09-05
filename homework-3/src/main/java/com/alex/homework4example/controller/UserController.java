package com.alex.homework4example.controller;

import com.alex.homework4example.dto.UserCreateDTO;
import com.alex.homework4example.dto.UserDTO;
import com.alex.homework4example.entity.User;
import com.alex.homework4example.mapper.impl.UserCreateMapper;
import com.alex.homework4example.mapper.impl.UserMapper;
import com.alex.homework4example.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;
    private final UserCreateMapper userCreateMapper;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserController(UserServiceImpl userService, UserMapper userMapper, UserCreateMapper userCreateMapper, ObjectMapper objectMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userCreateMapper = userCreateMapper;
        this.objectMapper = objectMapper;
    }

    public void createUsers() throws JsonProcessingException {
        UserCreateDTO user1DTO = UserCreateDTO.builder()
                .username("user1")
                .password("password1")
                .role("CUSTOMER")
                .build();

        UserCreateDTO user2DTO = UserCreateDTO.builder()
                .username("user2")
                .password("password2")
                .role("CUSTOMER")
                .build();

        User user1 = userCreateMapper.toEntity(user1DTO);
        User user2 = userCreateMapper.toEntity(user2DTO);

        userService.create(user1);
        userService.create(user2);

        System.out.println("Created User 1: " + objectMapper.writeValueAsString(userMapper.toDto(user1)));
        System.out.println("Created User 2: " + objectMapper.writeValueAsString(userMapper.toDto(user2)));
    }

    public void readUser(Long id) throws JsonProcessingException {
        User user = userService.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDTO userDTO = userMapper.toDto(user);
        System.out.println("Retrieved User: " + objectMapper.writeValueAsString(userDTO));
    }

    public void updateUser(Long id) throws JsonProcessingException {
        User user = userService.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDTO updatedUserDTO = UserDTO.builder()
                .id(user.getId())
                .username("updatedUser1")
                .role(user.getRole().name())
                .build();

        User updatedUser = userMapper.toEntity(updatedUserDTO);
        userService.update(updatedUser);

        System.out.println("Updated User: " + objectMapper.writeValueAsString(userMapper.toDto(updatedUser)));
    }

    public void deleteUser(Long id) {
        userService.delete(id);
        System.out.println("Deleted User with ID: " + id);
    }
}
