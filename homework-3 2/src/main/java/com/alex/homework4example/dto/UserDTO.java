package com.alex.homework4example.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private RoleDTO role;
    private String password;
}
