package com.alex.homework4example.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
public class CardDTO {

    private Long id;

    @NotNull(message = "Card number cannot be null")
    @Size(max = 20, message = "Card number must be at most 20 characters")
    private String cardNumber;

    @NotBlank(message = "Card type cannot be null")
    @Size(max = 20, message = "Card type must be at most 20 characters")
    private String cardType;

    @NotNull(message = "Expiration date cannot be null")
    @Future(message = "Expiration date must be in the future")
    private LocalDate expirationDate;

    @NotBlank(message = "CVV cannot be null")
    @Pattern(regexp = "\\d{3,4}", message = "CVV must be a 3 or 4 digit number")
    private String cvv;

    private Long accountId;

}
