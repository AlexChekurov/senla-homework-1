package com.alex.homework4example.mapper;

import com.alex.homework4example.dto.UserDTO;
import com.alex.homework4example.entity.Role;
import com.alex.homework4example.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper extends CommonMapper<User, UserDTO> {

    @Override
    @Mapping(target = "password", ignore = true)
    UserDTO toDto(User entity);

    default Role map(String value) {
        return new Role(null, value);
    }

    default String map(Role value) {
        return value.getName();
    }
}
