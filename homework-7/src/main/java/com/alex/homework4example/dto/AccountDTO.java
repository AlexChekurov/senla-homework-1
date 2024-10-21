package com.alex.homework4example.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private Long id;

    @NotBlank(message = "Account number cannot be null")
    @Size(max = 20, message = "Account number must be at most 20 characters")
    private String accountNumber;

    @NotBlank(message = "Account type cannot be null")
    @Size(max = 20, message = "Account type must be at most 20 characters")
    private String accountType;

    @NotNull(message = "Balance cannot be null")
    @Digits(
            integer = 15,
            fraction = 2,
            message = "Balance must be a valid number with up to 15 digits and 2 decimal places")
    private BigDecimal balance;

    @NotBlank(message = "Currency cannot be null")
    @Size(min = 3, max = 3, message = "Currency must be a 3-letter code")
    private String currency;

    @NotBlank(message = "IBAN cannot be null")
    private String iban;

    @NotNull(message = "Created at cannot be null")
    private LocalDateTime createdAt;

    private Long customerId;

}
