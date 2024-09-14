package com.alex.homework4example.service.impl;

import com.alex.homework4example.dao.Dao;
import com.alex.homework4example.dao.jdbc.impl.JdbcCardDao;
import com.alex.homework4example.entity.Card;
import com.alex.homework4example.service.AbstractCrudService;
import com.alex.homework4example.service.CardService;
import com.alex.homework4example.utils.ConnectionHolder;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl extends AbstractCrudService<Card> implements CardService {

    private final JdbcCardDao jdbcCardDao;

    public CardServiceImpl(ConnectionHolder connectionHolder) {
        this.jdbcCardDao = new JdbcCardDao(connectionHolder);
    }

    @Override
    protected Dao<Long, Card> getDao() {
        return jdbcCardDao;
    }
}
