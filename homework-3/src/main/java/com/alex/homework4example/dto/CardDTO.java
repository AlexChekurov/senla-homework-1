package com.alex.homework4example.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
@Builder
public class CardDTO {
    Long id;
    Long accountId;   // Ссылка на Account через ID
    Long customerId;  // Ссылка на Customer через ID
    String cardNumber;
    String cardType;
    LocalDate expirationDate;
    String cvv;
    LocalDateTime createdAt;
}