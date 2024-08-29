package com.alex.homework4example.controller;

import com.alex.homework4example.dto.CardDTO;
import com.alex.homework4example.service.impl.CardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CardController {

    private final CardService cardService;
    private final ObjectMapper objectMapper;

    @Autowired
    public CardController(CardService cardService, ObjectMapper objectMapper) {
        this.cardService = cardService;
        this.objectMapper = objectMapper;
    }

    public void initializeAndPerformOperations() throws JsonProcessingException {
        CardDTO card1 = CardDTO.builder()
                .accountId(1)
                .customerId(1)
                .cardNumber("1234-5678-9012-3456")
                .cardType("VISA")
                .expirationDate(LocalDate.now().plusYears(2))
                .cvv("123")
                .build();

        CardDTO card2 = CardDTO.builder()
                .accountId(2)
                .customerId(2)
                .cardNumber("6543-2109-8765-4321")
                .cardType("MASTERCARD")
                .expirationDate(LocalDate.now().plusYears(3))
                .cvv("456")
                .build();

        CardDTO createdCard1 = cardService.create(card1);
        CardDTO createdCard2 = cardService.create(card2);

        System.out.println("Retrieved Card 1: " + objectMapper.writeValueAsString(cardService.findById(createdCard1.getId()).orElseThrow()));

        cardService.delete(createdCard2.getId());

        CardDTO updatedCard1 = CardDTO.builder()
                .id(createdCard1.getId())
                .accountId(createdCard1.getAccountId())
                .customerId(createdCard1.getCustomerId())
                .cardNumber(createdCard1.getCardNumber())
                .cardType("UPDATED_VISA")
                .expirationDate(createdCard1.getExpirationDate())
                .cvv(createdCard1.getCvv())
                .build();
        cardService.update(updatedCard1);

        System.out.println("Updated Card 1: " + objectMapper.writeValueAsString(cardService.findById(updatedCard1.getId()).orElseThrow()));
    }
}
