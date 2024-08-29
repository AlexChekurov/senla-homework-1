package com.alex.homework4example.mapper.impl;

import com.alex.homework4example.dto.UserDTO;
import com.alex.homework4example.entity.Role;
import com.alex.homework4example.entity.User;
import com.alex.homework4example.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<User, UserDTO> {

    @Override
    public UserDTO toDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(String.valueOf(user.getRole()))
                .build();
    }

    @Override
    public User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .role(Role.valueOf(userDTO.getRole()))
                .build();
    }
}
