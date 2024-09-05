package com.alex.homework4example.dao.impl;

import com.alex.homework4example.dao.AbstractDao;
import com.alex.homework4example.entity.Card;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class CardDao extends AbstractDao<Long, Card> {

    private final static AtomicLong idCounter = new AtomicLong(0);

    @Override
    protected Long generateId() {
        return idCounter.incrementAndGet();
    }

    @Override
    protected Long getId(Card card) {
        return card.getId() != null ? card.getId() : null;
    }

    @Override
    protected void setId(Card card, Long id) {
        card.setId(id);
    }
}
