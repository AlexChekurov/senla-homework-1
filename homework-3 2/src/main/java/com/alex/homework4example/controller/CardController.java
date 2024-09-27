package com.alex.homework4example.controller;

import com.alex.homework4example.dto.CardDTO;
import com.alex.homework4example.exception.EntityNotFoundException;
import com.alex.homework4example.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class CardController {

    private final CardService cardService;

    public List<CardDTO> getAllCards() {
        return cardService.findAll();
    }

    public CardDTO getCardById(Long id) {
        return cardService.findDtoById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find card with id: " + id));
    }

    public CardDTO createCard(CardDTO card) {
        return cardService.create(card);
    }

    public CardDTO updateCard(Long id, CardDTO cardDetails) {
        return cardService.findById(id)
                .map(card -> {
                    card.setCardNumber(cardDetails.getCardNumber());
                    card.setCardType(cardDetails.getCardType());
                    card.setExpirationDate(cardDetails.getExpirationDate());
                    card.setCvv(cardDetails.getCvv());
                    return cardService.updateEntityToDto(card);
                })
                .orElseThrow(() -> new EntityNotFoundException("Can't update card with id: " + id));
    }

    public void deleteCard(Long id) {
        cardService.deleteById(id);
    }
}
