package com.alex.homework4example.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private Long id;

    @Size(max = 100, message = "Street must be at most 100 characters")
    private String street;

    @Size(max = 50, message = "City must be at most 50 characters")
    private String city;

    @Size(max = 20, message = "Postal code must be at most 20 characters")
    private String postalCode;
}
