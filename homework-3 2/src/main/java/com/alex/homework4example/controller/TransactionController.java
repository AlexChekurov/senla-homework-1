package com.alex.homework4example.controller;

import com.alex.homework4example.dto.TransactionDTO;
import com.alex.homework4example.exception.EntityNotFoundException;
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
        return transactionService.findDtoById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find transaction with id: " + id));
    }

    public TransactionDTO createTransaction(TransactionDTO transaction) {
        return transactionService.create(transaction);
    }

    public TransactionDTO updateTransaction(Long id, TransactionDTO transactionDetails) {
        return transactionService.findById(id)
                .map(transaction -> {
                    transaction.setAmount(transactionDetails.getAmount());
                    transaction.setTransactionDate(transactionDetails.getTransactionDate());
                    transaction.setCurrency(transactionDetails.getCurrency());
                    return transactionService.updateEntityToDto(transaction);
                })
                .orElseThrow(() -> new EntityNotFoundException("Can't update transaction with id: " + id));
    }

    public void deleteTransaction(Long id) {
        transactionService.deleteById(id);
    }
}
