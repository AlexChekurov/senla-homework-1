package com.alex.homework4example.service.impl;

import com.alex.homework4example.dao.impl.TransactionDao;
import com.alex.homework4example.entity.Transaction;
import com.alex.homework4example.service.TransactionService;
import com.alex.homework4example.service.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl extends AbstractCrudService<Transaction> implements TransactionService {

    private final TransactionDao transactionDao;

    public TransactionServiceImpl(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    protected TransactionDao getDao() {
        return transactionDao;
    }

    @Override
    protected Long getEntityId(Transaction transaction) {
        return transaction.getId() != null ? transaction.getId() : null;
    }
}
