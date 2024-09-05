package com.alex.homework4example.service.impl;

import com.alex.homework4example.dao.impl.CardDao;
import com.alex.homework4example.entity.Card;
import com.alex.homework4example.service.CardService;
import com.alex.homework4example.service.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl extends AbstractCrudService<Card> implements CardService {

    private final CardDao cardDao;

    public CardServiceImpl(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    @Override
    protected CardDao getDao() {
        return cardDao;
    }

    @Override
    protected Long getEntityId(Card card) {
        return card.getId() != null ? card.getId() : null;
    }
}
