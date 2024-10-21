package com.alex.homework4example.dto;

import jakarta.validation.Valid;
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
public class TransactionDTO {

    private Long id;

    @NotNull(message = "Amount cannot be null")
    @Digits(
            integer = 15,
            fraction = 2,
            message = "Amount must be a valid number with up to 15 digits and 2 decimal places")
    private BigDecimal amount;

    private LocalDateTime transactionDate;

    @NotNull
    @Valid
    private AccountDTO fromAccount;

    @NotNull
    @Valid
    private AccountDTO toAccount;

    @NotBlank(message = "Currency cannot be null")
    @Size(min = 3, max = 3, message = "Currency must be a 3-letter code")
    private String currency;

}
