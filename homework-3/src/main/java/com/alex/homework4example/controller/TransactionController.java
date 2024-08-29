package com.alex.homework4example.controller;

import com.alex.homework4example.dto.TransactionDTO;
import com.alex.homework4example.service.impl.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class TransactionController {

    private final TransactionService transactionService;
    private final ObjectMapper objectMapper;

    @Autowired
    public TransactionController(TransactionService transactionService, ObjectMapper objectMapper) {
        this.transactionService = transactionService;
        this.objectMapper = objectMapper;
    }

    public void initializeAndPerformOperations() throws JsonProcessingException {
        TransactionDTO transaction1 = TransactionDTO.builder()
                .amount(new BigDecimal("100.00"))
                .transactionDate(LocalDateTime.now())
                .sourceAccountId(1)
                .destinationAccountId(2)
                .currency("USD")
                .build();

        TransactionDTO transaction2 = TransactionDTO.builder()
                .amount(new BigDecimal("200.00"))
                .transactionDate(LocalDateTime.now())
                .sourceAccountId(2)
                .destinationAccountId(1)
                .currency("EUR")
                .build();

        TransactionDTO createdTransaction1 = transactionService.create(transaction1);
        TransactionDTO createdTransaction2 = transactionService.create(transaction2);

        System.out.println("Retrieved Transaction 1: " + objectMapper.writeValueAsString(transactionService.findById(createdTransaction1.getId()).orElseThrow()));

        transactionService.delete(createdTransaction2.getId());

        TransactionDTO updatedTransaction1 = TransactionDTO.builder()
                .id(createdTransaction1.getId())
                .amount(new BigDecimal("150.00"))
                .transactionDate(createdTransaction1.getTransactionDate())
                .sourceAccountId(createdTransaction1.getSourceAccountId())
                .destinationAccountId(createdTransaction1.getDestinationAccountId())
                .currency(createdTransaction1.getCurrency())
                .build();
        transactionService.update(updatedTransaction1);

        System.out.println("Updated Transaction 1: " + objectMapper.writeValueAsString(transactionService.findById(updatedTransaction1.getId()).orElseThrow()));
    }
}
