package com.alex.homework4example.controller;

import com.alex.homework4example.dto.UserDTO;
import com.alex.homework4example.exception.EntityNotFoundException;
import com.alex.homework4example.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    public UserDTO getUserById(Long id) {
        return userService.findDtoById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find user with id: " + id));
    }

    public UserDTO createUser(UserDTO user) {
        return userService.create(user);
    }

    public UserDTO updateUser(Long id, UserDTO userDetails) {
        return userService.findById(id)
                .map(user -> {
                    user.setUsername(userDetails.getUsername());
                    user.setPassword(userDetails.getPassword());
                    return userService.updateEntityToDto(user);
                })
                .orElseThrow(() -> new EntityNotFoundException("Can't update user with id: " + id));
    }

    public void deleteUser(Long id) {
        userService.deleteById(id);
    }
}
