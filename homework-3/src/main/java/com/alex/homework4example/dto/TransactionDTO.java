package com.alex.homework4example.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class TransactionDTO {
    Long id;
    BigDecimal amount;
    LocalDateTime transactionDate;
    Long sourceAccountId;
    Long destinationAccountId;
    String currency;
}