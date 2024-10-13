package com.alex.homework4example.dto;

import lombok.Data;

@Data
public class UserCreateDTO {
    private String username;
    private String password;
    private String role;
}
