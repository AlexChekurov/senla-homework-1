package com.alex.homework4example.mapper.impl;

import com.alex.homework4example.dto.UserCreateDTO;
import com.alex.homework4example.entity.Role;
import com.alex.homework4example.entity.User;
import com.alex.homework4example.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class UserCreateMapper implements Mapper<User, UserCreateDTO> {

    @Override
    public UserCreateDTO toDto(User user) {
        return UserCreateDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .role(String.valueOf(user.getRole()))
                .build();
    }

    @Override
    public User toEntity(UserCreateDTO userCreateDTO) {
        return User.builder()
                .username(userCreateDTO.getUsername())
                .password(userCreateDTO.getPassword())
                .role(Role.valueOf(userCreateDTO.getRole()))
                .build();
    }
}
