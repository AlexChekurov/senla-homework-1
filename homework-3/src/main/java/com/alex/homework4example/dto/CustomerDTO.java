package com.alex.homework4example.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CustomerDTO {
    Integer id;
    String firstName;
    String lastName;
    String email;
    String phone;
    String street;
    String city;
    String state;
    String postalCode;
    String country;
    Integer userId;
}
