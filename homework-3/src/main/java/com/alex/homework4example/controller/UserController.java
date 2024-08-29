package com.alex.homework4example.controller;

import com.alex.homework4example.dto.UserDTO;
import com.alex.homework4example.service.impl.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserController {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserController(UserService userService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.objectMapper = objectMapper;
    }

    public void initializeAndPerformOperations() {
        try {
            UserDTO user1 = UserDTO.builder()
                    .username("user1")
                    .password("password1")
                    .role("CUSTOMER")
                    .build();

            UserDTO user2 = UserDTO.builder()
                    .username("user2")
                    .password("password2")
                    .role("CUSTOMER")
                    .build();

            UserDTO createdUser1 = userService.create(user1);
            UserDTO createdUser2 = userService.create(user2);

            String userJson = objectMapper.writeValueAsString(userService.findById(createdUser1.getId()).orElse(null));
            System.out.println("Retrieved User 1: " + userJson);

            userService.delete(createdUser2.getId());

            createdUser1 = UserDTO.builder()
                    .id(createdUser1.getId())
                    .username("updatedUser1")
                    .password(createdUser1.getPassword())
                    .role(createdUser1.getRole())
                    .build();
            userService.update(createdUser1);

            userJson = objectMapper.writeValueAsString(userService.findById(createdUser1.getId()).orElse(null));
            System.out.println("Updated User 1: " + userJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
