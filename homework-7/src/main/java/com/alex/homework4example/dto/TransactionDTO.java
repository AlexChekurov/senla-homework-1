package com.alex.homework4example.dto;

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
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private Long sourceAccountId;
    private Long destinationAccountId;
    private String currency;
}
