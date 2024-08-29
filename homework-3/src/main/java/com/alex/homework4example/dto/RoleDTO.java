package com.alex.homework4example.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RoleDTO {
    String name;  // ADMIN, EMPLOYEE, CUSTOMER
}
