package com.alex.homework4example.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private Long id;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private Long sourceAccountId;
    private Long destinationAccountId;
    private String currency;
}
