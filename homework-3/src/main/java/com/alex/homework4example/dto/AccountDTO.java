package com.alex.homework4example.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class AccountDTO {
    Long id;
    String accountNumber;
    String accountType;
    BigDecimal balance;
    String currency;
    String iban;
    LocalDateTime createdAt;
}