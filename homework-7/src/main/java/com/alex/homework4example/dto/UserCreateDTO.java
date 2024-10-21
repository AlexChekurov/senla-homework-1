package com.alex.homework4example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class UserCreateDTO {

    @NotBlank(message = "Username cannot be null")
    private String username;

    @NotBlank(message = "Password cannot be null")
    private String password;

    @NotBlank(message = "Role cannot be null")
    private String role;

}
