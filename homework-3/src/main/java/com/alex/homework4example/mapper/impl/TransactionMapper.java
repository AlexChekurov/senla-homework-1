package com.alex.homework4example.mapper.impl;

import com.alex.homework4example.dto.TransactionDTO;
import com.alex.homework4example.entity.Transaction;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.dao.impl.AccountDao;
import com.alex.homework4example.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper implements Mapper<Transaction, TransactionDTO> {

    private final AccountDao accountDao;

    public TransactionMapper(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public TransactionDTO toDto(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .amount(transaction.getAmount())
                .transactionDate(transaction.getTransactionDate())
                .sourceAccountId(transaction.getSourceAccount().getId())
                .destinationAccountId(transaction.getDestinationAccount().getId())
                .currency(transaction.getCurrency())
                .build();
    }

    @Override
    public Transaction toEntity(TransactionDTO transactionDTO) {
        Account sourceAccount = accountDao.findById(transactionDTO.getSourceAccountId()).orElseThrow();
        Account destinationAccount = accountDao.findById(transactionDTO.getDestinationAccountId()).orElseThrow();
        return Transaction.builder()
                .id(transactionDTO.getId())
                .amount(transactionDTO.getAmount())
                .transactionDate(transactionDTO.getTransactionDate())
                .sourceAccount(sourceAccount)
                .destinationAccount(destinationAccount)
                .currency(transactionDTO.getCurrency())
                .build();
    }
}
