package com.alex.homework4example.service.impl;

import com.alex.homework4example.dao.Dao;
import com.alex.homework4example.dao.jdbc.impl.JdbcTransactionDao;
import com.alex.homework4example.entity.Transaction;
import com.alex.homework4example.service.AbstractCrudService;
import com.alex.homework4example.service.TransactionService;
import com.alex.homework4example.utils.ConnectionHolder;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl extends AbstractCrudService<Transaction> implements TransactionService {

    private final JdbcTransactionDao jdbcTransactionDao;

    public TransactionServiceImpl(ConnectionHolder connectionHolder) {
        this.jdbcTransactionDao = new JdbcTransactionDao(connectionHolder);
    }

    @Override
    protected Dao<Long, Transaction> getDao() {
        return jdbcTransactionDao;
    }
}
