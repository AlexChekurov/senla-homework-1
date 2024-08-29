package com.alex.homework4example.dao.impl;

import com.alex.homework4example.dao.AbstractDao;
import com.alex.homework4example.entity.Transaction;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TransactionDao extends AbstractDao<Integer, Transaction> {

    private final static AtomicInteger idCounter = new AtomicInteger(0);

    @Override
    protected Integer generateId() {
        return idCounter.incrementAndGet();
    }

    @Override
    protected Integer getId(Transaction transaction) {
        return transaction.getId();
    }

    @Override
    protected void setId(Transaction transaction, Integer id) {
        transaction.setId(id);
    }
}
