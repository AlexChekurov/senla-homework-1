package com.alex.homework4example.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class CustomerDTO {
    Long id;
    String firstName;
    String lastName;
    String email;
    String phone;
    String street;
    String city;
    String state;
    String postalCode;
    String country;
    LocalDateTime createdAt;
    Long userId;
}
