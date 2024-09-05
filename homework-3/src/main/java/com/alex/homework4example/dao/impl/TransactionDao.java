package com.alex.homework4example.dao.impl;

import com.alex.homework4example.dao.AbstractDao;
import com.alex.homework4example.entity.Transaction;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class TransactionDao extends AbstractDao<Long, Transaction> {

    private final static AtomicLong idCounter = new AtomicLong(0);

    @Override
    protected Long generateId() {
        return idCounter.incrementAndGet();
    }

    @Override
    protected Long getId(Transaction transaction) {
        return transaction.getId() != null ? transaction.getId() : null;
    }

    @Override
    protected void setId(Transaction transaction, Long id) {
        transaction.setId(id);
    }
}
