package com.alex.homework4example.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CardDTO {
    private Long id;
    private Long accountId;
    private Long customerId;
    private String cardNumber;
    private String cardType;
    private LocalDate expirationDate;
    private String cvv;
    private LocalDateTime createdAt;
    private AccountDTO account;
}
