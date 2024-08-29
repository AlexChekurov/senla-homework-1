package com.alex.homework4example.service.impl;

import com.alex.homework4example.dao.impl.TransactionDao;
import com.alex.homework4example.dto.TransactionDTO;
import com.alex.homework4example.entity.Transaction;
import com.alex.homework4example.mapper.impl.TransactionMapper;
import com.alex.homework4example.service.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class TransactionService extends AbstractCrudService<Transaction, TransactionDTO, Integer> {

    private final TransactionDao transactionDao;
    private final TransactionMapper transactionMapper;

    public TransactionService(TransactionDao transactionDao, TransactionMapper transactionMapper) {
        this.transactionDao = transactionDao;
        this.transactionMapper = transactionMapper;
    }

    @Override
    protected TransactionDao getDao() {
        return transactionDao;
    }

    @Override
    protected TransactionMapper getMapper() {
        return transactionMapper;
    }

    @Override
    protected Integer getEntityId(Transaction transaction) {
        return transaction.getId();
    }
}
