package com.alex.homework4example.dao.impl;

import com.alex.homework4example.dao.AbstractDao;
import com.alex.homework4example.entity.Card;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CardDao extends AbstractDao<Integer, Card> {

    private final static AtomicInteger idCounter = new AtomicInteger(0);

    @Override
    protected Integer generateId() {
        return idCounter.incrementAndGet();
    }

    @Override
    protected Integer getId(Card card) {
        return card.getId();
    }

    @Override
    protected void setId(Card card, Integer id) {
        card.setId(id);
    }
}
