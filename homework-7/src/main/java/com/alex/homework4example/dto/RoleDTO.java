package com.alex.homework4example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Long id;

    @NotBlank(message = "Role name cannot be null")
    @Size(max = 50, message = "Role name must be at most 50 characters")
    private String name;
}
