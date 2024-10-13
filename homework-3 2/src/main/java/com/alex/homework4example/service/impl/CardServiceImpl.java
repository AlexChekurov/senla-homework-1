package com.alex.homework4example.service.impl;

import com.alex.homework4example.dto.CardDTO;
import com.alex.homework4example.entity.Card;
import com.alex.homework4example.exception.EntityNotFoundException;
import com.alex.homework4example.mapper.CommonMapper;
import com.alex.homework4example.repository.AbstractRepository;
import com.alex.homework4example.service.CardService;
import org.springframework.stereotype.Service;


@Service
public class CardServiceImpl extends AbstractCrudService<Card, CardDTO> implements CardService {

    public CardServiceImpl(AbstractRepository<Card> repository, CommonMapper<Card, CardDTO> mapper) {
        super(repository, mapper);
    }

    @Override
    public CardDTO update(Long accountId, CardDTO cardDetails) {
        return findById(accountId)
                .map(card -> {
                    card.setCardNumber(cardDetails.getCardNumber());
                    card.setCardType(cardDetails.getCardType());
                    card.setExpirationDate(cardDetails.getExpirationDate());
                    card.setCvv(cardDetails.getCvv());
                    return updateEntityToDto(card);
                })
                .orElseThrow(() -> new EntityNotFoundException("Can't update card with id: " + accountId));
    }

}
