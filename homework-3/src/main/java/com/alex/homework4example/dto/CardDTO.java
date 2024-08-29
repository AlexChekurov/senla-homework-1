package com.alex.homework4example.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
@Builder
public class CardDTO {
    Integer id;
    Integer accountId;
    Integer customerId;
    String cardNumber;
    String cardType;
    LocalDate expirationDate;
    String cvv;
    LocalDateTime createdAt;
}
