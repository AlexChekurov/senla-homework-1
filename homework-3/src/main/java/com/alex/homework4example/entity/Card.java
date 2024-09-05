package com.alex.homework4example.entity;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {
    private Long id;
    private Account account;
    private Customer customer;
    private String cardNumber;
    private String cardType;
    private LocalDate expirationDate;
    private String cvv;
    private LocalDateTime createdAt;
}
