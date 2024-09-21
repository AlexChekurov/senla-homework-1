package com.alex.homework4example.mapper;

import com.alex.homework4example.dto.UserCreateDTO;
import com.alex.homework4example.dto.UserDTO;
import com.alex.homework4example.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-21T21:45:10+0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 20.0.2.1 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserCreateDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.username( userDTO.getUsername() );
        user.password( userDTO.getPassword() );
        user.role( toRole( userDTO.getRole() ) );

        return user.build();
    }

    @Override
    public UserDTO toDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO.UserDTOBuilder userDTO = UserDTO.builder();

        userDTO.id( user.getId() );
        userDTO.username( user.getUsername() );
        userDTO.role( toRoleString( user.getRole() ) );

        return userDTO.build();
    }
}
