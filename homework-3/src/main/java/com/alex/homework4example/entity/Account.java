package com.alex.homework4example.entity;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    private Long id;
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private String currency;
    private String iban;
    private LocalDateTime createdAt;

    @Builder.Default
    private List<Card> cards = new ArrayList<>(); // Список карт, привязанных к аккаунту
}