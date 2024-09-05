package com.alex.homework4example.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserCreateDTO {
    String username;
    String password;
    String role;
}