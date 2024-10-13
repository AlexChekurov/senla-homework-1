package com.alex.homework4example.controller;

import com.alex.homework4example.dto.TransactionDTO;
import com.alex.homework4example.exception.EntityNotFoundException;
import com.alex.homework4example.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public List<TransactionDTO> getAllTransactions() {
        return transactionService.findAll();
    }

    @GetMapping("/{id}")
    public TransactionDTO getTransactionById(@PathVariable("id") Long id) {
        return transactionService.findDtoById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find transaction with id: " + id));
    }

    @PostMapping
    public TransactionDTO createTransaction(@RequestBody TransactionDTO transaction) {
        return transactionService.create(transaction);
    }

    @PutMapping("/{id}")
    public TransactionDTO updateTransaction(
            @PathVariable("id") Long id,
            @RequestBody TransactionDTO transactionDetails) {
        return transactionService.findById(id)
                .map(transaction -> {
                    transaction.setAmount(transactionDetails.getAmount());
                    transaction.setTransactionDate(transactionDetails.getTransactionDate());
                    transaction.setCurrency(transactionDetails.getCurrency());
                    return transactionService.updateEntityToDto(transaction);
                })
                .orElseThrow(() -> new EntityNotFoundException("Can't update transaction with id: " + id));
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable("id") Long id) {
        transactionService.deleteById(id);
    }

}
