package com.alex.homework4example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomerDTO {
    private Long id;

    @NotBlank(message = "First name cannot be null")
    @Size(max = 50, message = "First name must be at most 50 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be null")
    @Size(max = 50, message = "Last name must be at most 50 characters")
    private String lastName;

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must be at most 100 characters")
    private String email;

    @NotBlank(message = "Phone number cannot be null")
    @Pattern(regexp = "\\+?\\d{1,15}", message = "Phone number must be valid")
    private String phone;

    private Long addressId;

    @NotNull(message = "User ID cannot be null")
    private Long userId;
}
