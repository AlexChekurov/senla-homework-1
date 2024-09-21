package com.alex.homework4example.controller;

import com.alex.homework4example.dto.UserCreateDTO;
import com.alex.homework4example.entity.Role;
import com.alex.homework4example.entity.User;
import com.alex.homework4example.mapper.UserMapper;
import com.alex.homework4example.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserController(UserServiceImpl userService, UserMapper userMapper, ObjectMapper objectMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.objectMapper = objectMapper;
    }

    public List<User> createUsers(Map<String, Role> roles) throws JsonProcessingException {
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

        User user1 = userMapper.toEntity(user1DTO);
        user1.setRole(roles.get(user1DTO.getRole()));
        User user2 = userMapper.toEntity(user2DTO);
        user2.setRole(roles.get(user2DTO.getRole()));

        userService.create(user1);
        userService.create(user2);

        System.out.println("Created User 1: " + objectMapper.writeValueAsString(userMapper.toDto(user1)));
        System.out.println("Created User 2: " + objectMapper.writeValueAsString(userMapper.toDto(user2)));

        return List.of(user1, user2);
    }
}
