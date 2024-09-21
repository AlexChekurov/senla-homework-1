package com.alex.homework4example.mapper;

import com.alex.homework4example.dto.UserCreateDTO;
import com.alex.homework4example.dto.UserDTO;
import com.alex.homework4example.entity.Role;
import com.alex.homework4example.entity.User;
import com.alex.homework4example.enumeration.RoleName;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    User toEntity(UserCreateDTO userDTO);

    UserDTO toDto(User user);

    default Role toRole(String roleName) {
        return Role.builder()
                .name(RoleName.valueOf(roleName))
                .build();
    }

    default String toRoleString(Role role) {
        return role.getName().name();
    }
}
