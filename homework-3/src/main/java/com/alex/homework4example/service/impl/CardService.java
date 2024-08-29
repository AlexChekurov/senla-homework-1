package com.alex.homework4example.service.impl;

import com.alex.homework4example.dao.impl.CardDao;
import com.alex.homework4example.dto.CardDTO;
import com.alex.homework4example.entity.Card;
import com.alex.homework4example.mapper.impl.CardMapper;
import com.alex.homework4example.service.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class CardService extends AbstractCrudService<Card, CardDTO, Integer> {

    private final CardDao cardDao;
    private final CardMapper cardMapper;

    public CardService(CardDao cardDao, CardMapper cardMapper) {
        this.cardDao = cardDao;
        this.cardMapper = cardMapper;
    }

    @Override
    protected CardDao getDao() {
        return cardDao;
    }

    @Override
    protected CardMapper getMapper() {
        return cardMapper;
    }

    @Override
    protected Integer getEntityId(Card card) {
        return card.getId();
    }
}
