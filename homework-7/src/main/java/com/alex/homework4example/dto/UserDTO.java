package com.alex.homework4example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank(message = "Username cannot be null")
    @Size(max = 50, message = "Username must be at most 50 characters")
    private String username;

    @Size(max = 255, message = "Password must be at most 255 characters")
    private String password;

    @NotNull(message = "Role ID cannot be null")
    private Long roleId;

}
