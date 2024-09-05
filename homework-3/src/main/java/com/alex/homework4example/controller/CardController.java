package com.alex.homework4example.controller;

import com.alex.homework4example.dto.CardDTO;
import com.alex.homework4example.entity.Card;
import com.alex.homework4example.mapper.impl.CardMapper;
import com.alex.homework4example.service.impl.CardServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardController {

    private final CardServiceImpl cardService;
    private final CardMapper cardMapper;
    private final ObjectMapper objectMapper;

    @Autowired
    public CardController(CardServiceImpl cardService, CardMapper cardMapper, ObjectMapper objectMapper) {
        this.cardService = cardService;
        this.cardMapper = cardMapper;
        this.objectMapper = objectMapper;
    }

    public void createCards() throws JsonProcessingException {
        CardDTO card1DTO = CardDTO.builder()
                .accountId(1L)
                .customerId(1L)
                .cardNumber("1234-5678-9012-3456")
                .cardType("VISA")
                .expirationDate(java.time.LocalDate.now().plusYears(2))
                .cvv("123")
                .build();

        CardDTO card2DTO = CardDTO.builder()
                .accountId(2L)
                .customerId(2L)
                .cardNumber("6543-2109-8765-4321")
                .cardType("MASTERCARD")
                .expirationDate(java.time.LocalDate.now().plusYears(3))
                .cvv("456")
                .build();

        Card card1 = cardMapper.toEntity(card1DTO);
        Card card2 = cardMapper.toEntity(card2DTO);

        cardService.create(card1);
        cardService.create(card2);

        System.out.println("Created Card 1: " + objectMapper.writeValueAsString(cardMapper.toDto(card1)));
        System.out.println("Created Card 2: " + objectMapper.writeValueAsString(cardMapper.toDto(card2)));
    }

    public void readCard(Long id) throws JsonProcessingException {
        Card card = cardService.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        CardDTO cardDTO = cardMapper.toDto(card);
        System.out.println("Retrieved Card: " + objectMapper.writeValueAsString(cardDTO));
    }

    public void updateCard(Long id) throws JsonProcessingException {
        Card card = cardService.findById(id)
                .orElseThrow(() -> new RuntimeException("Card not found"));

        CardDTO updatedCardDTO = CardDTO.builder()
                .id(card.getId())
                .accountId(card.getAccount().getId())
                .customerId(card.getCustomer().getId())
                .cardNumber(card.getCardNumber())
                .cardType("UPDATED_VISA")
                .expirationDate(card.getExpirationDate())
                .cvv(card.getCvv())
                .createdAt(card.getCreatedAt())
                .build();

        Card updatedCard = cardMapper.toEntity(updatedCardDTO);
        cardService.update(updatedCard);

        System.out.println("Updated Card: " + objectMapper.writeValueAsString(cardMapper.toDto(updatedCard)));
    }

    public void deleteCard(Long id) {
        cardService.delete(id);
        System.out.println("Deleted Card with ID: " + id);
    }
}
