package com.alex.homework4example.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    private Integer id;
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private String currency;
    private String iban;
    private LocalDateTime createdAt;
}
