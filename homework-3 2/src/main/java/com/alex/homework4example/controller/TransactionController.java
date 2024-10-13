package com.alex.homework4example.controller;

import com.alex.homework4example.dto.TransactionDTO;
import com.alex.homework4example.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@AllArgsConstructor
@Component
public class TransactionController {

    private final TransactionService transactionService;

    public List<TransactionDTO> getAllTransactions() {
        return transactionService.findAll();
    }

    public TransactionDTO getTransactionById(Long id) {
        return transactionService.findDtoById(id);
    }

    public TransactionDTO createTransaction(TransactionDTO transaction) {
        return transactionService.create(transaction);
    }

    public TransactionDTO updateTransaction(Long id, TransactionDTO transactionDetails) {
        return transactionService.update(id, transactionDetails);
    }

    public void deleteTransaction(Long id) {
        transactionService.deleteById(id);
    }

}
