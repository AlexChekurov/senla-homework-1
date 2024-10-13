package com.alex.homework4example.service.impl;

import com.alex.homework4example.dto.CardDTO;
import com.alex.homework4example.entity.Card;
import com.alex.homework4example.mapper.CommonMapper;
import com.alex.homework4example.repository.AbstractRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    private AbstractRepository<Card> cardRepository;

    @Mock
    private CommonMapper<Card, CardDTO> cardMapper;

    @InjectMocks
    private CardServiceImpl cardService;

    private Card card;
    private CardDTO cardDTO;

    @BeforeEach
    public void setUp() {
        card = new Card();
        card.setId(1L);
        card.setCardNumber("1234567890123456");
        card.setCardType("Visa");
        card.setCvv("123");

        cardDTO = new CardDTO();
        cardDTO.setId(1L);
        cardDTO.setCardNumber("1234567890123456");
        cardDTO.setCardType("Visa");
        cardDTO.setCvv("123");
    }

    @Test
    void testCreateCard() {
        //given
        when(cardMapper.toEntity(any(CardDTO.class))).thenReturn(card);
        when(cardRepository.create(any(Card.class))).thenReturn(card);
        when(cardMapper.toDto(any(Card.class))).thenReturn(cardDTO);

        //when
        CardDTO createdCardDTO = cardService.create(cardDTO);

        //then
        assertNotNull(createdCardDTO);
        assertEquals(cardDTO.getCardNumber(), createdCardDTO.getCardNumber());
        verify(cardMapper).toEntity(cardDTO);
        verify(cardRepository).create(card);
        verify(cardMapper).toDto(card);
    }

    @Test
    void testFindById() {
        //given
        when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
        when(cardMapper.toDto(card)).thenReturn(cardDTO);

        //when
        CardDTO foundCardDTO = cardService.findDtoById(1L);

        //then
        assertEquals(cardDTO.getId(), foundCardDTO.getId());
        verify(cardRepository).findById(1L);
    }

    @Test
    void testUpdateCard() {
        //given
        when(cardRepository.update(any(Card.class))).thenReturn(card);
        when(cardRepository.findById(card.getId())).thenReturn(Optional.ofNullable(card));
        when(cardMapper.toDto(any(Card.class))).thenReturn(cardDTO);

        //when
        CardDTO updatedCardDTO = cardService.update(card.getId(), cardDTO);

        //then
        assertNotNull(updatedCardDTO);
        assertEquals(cardDTO.getCardNumber(), updatedCardDTO.getCardNumber());
        verify(cardRepository).update(card);
        verify(cardMapper).toDto(card);
    }

    @Test
    void testDeleteById() {
        //given
        when(cardRepository.deleteById(1L)).thenReturn(true);

        //when
        boolean result = cardService.deleteById(1L);

        //then
        assertTrue(result);
        verify(cardRepository).deleteById(1L);
    }

    @Test
    void testFindAll() {
        //given
        when(cardRepository.findAll()).thenReturn(List.of(card));
        when(cardMapper.toDto(any(Card.class))).thenReturn(cardDTO);

        //when
        List<CardDTO> allCards = cardService.findAll();

        //then
        assertEquals(1, allCards.size());
        assertEquals(cardDTO.getCardNumber(), allCards.get(0).getCardNumber());
        verify(cardRepository).findAll();
    }

}
