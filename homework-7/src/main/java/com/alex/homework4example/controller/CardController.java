package com.alex.homework4example.controller;

import com.alex.homework4example.dto.CardDTO;
import com.alex.homework4example.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/cards")
public class CardController {

    private final CardService cardService;

    @GetMapping
    public List<CardDTO> getAllCards() {
        return cardService.findAll();
    }

    @GetMapping("/{id}")
    public CardDTO getCardById(@PathVariable("id") Long id) {
        return cardService.findDtoById(id);
    }

    @PostMapping
    public CardDTO createCard(@RequestBody CardDTO card) {
        return cardService.create(card);
    }

    @PutMapping("/{id}")
    public CardDTO updateCard(@PathVariable("id") Long id, @RequestBody CardDTO cardDetails) {
        return cardService.update(id, cardDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteCard(@PathVariable("id") Long id) {
        cardService.deleteById(id);
    }

}
