package com.alex.homework4example.mapper.impl;

import com.alex.homework4example.dto.TransactionDTO;
import com.alex.homework4example.entity.Transaction;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.dao.jdbc.impl.JdbcAccountDao;
import com.alex.homework4example.exception.EntityNotFoundException;
import com.alex.homework4example.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper implements Mapper<Transaction, TransactionDTO> {

    private final JdbcAccountDao jdbcAccountDao;

    public TransactionMapper(JdbcAccountDao jdbcAccountDao) {
        this.jdbcAccountDao = jdbcAccountDao;
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
        Account sourceAccount = jdbcAccountDao.findById(transactionDTO.getSourceAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Source account with ID " + transactionDTO.getSourceAccountId() + " not found"));

        Account destinationAccount = jdbcAccountDao.findById(transactionDTO.getDestinationAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Destination account with ID " + transactionDTO.getDestinationAccountId() + " not found"));

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
