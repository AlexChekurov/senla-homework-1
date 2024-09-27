package com.alex.homework4example.mapper;

import com.alex.homework4example.dto.RoleDTO;
import com.alex.homework4example.entity.Role;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-26T10:05:20+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.2 (Eclipse Adoptium)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public Role toEntity(RoleDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Role.RoleBuilder role = Role.builder();

        role.id( dto.getId() );
        role.name( dto.getName() );

        return role.build();
    }

    @Override
    public RoleDTO toDto(Role entity) {
        if ( entity == null ) {
            return null;
        }

        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setId( entity.getId() );
        roleDTO.setName( entity.getName() );

        return roleDTO;
    }
}
