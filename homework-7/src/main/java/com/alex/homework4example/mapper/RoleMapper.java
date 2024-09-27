package com.alex.homework4example.mapper;

import com.alex.homework4example.dto.RoleDTO;
import com.alex.homework4example.entity.Role;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface RoleMapper extends CommonMapper<Role, RoleDTO> {

}
