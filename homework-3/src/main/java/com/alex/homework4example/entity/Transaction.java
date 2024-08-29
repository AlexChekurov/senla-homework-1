package com.alex.homework4example.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    private Integer id;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private Account sourceAccount;
    private Account destinationAccount;
    private String currency;
}
