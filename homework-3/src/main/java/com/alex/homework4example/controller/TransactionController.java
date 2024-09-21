//package com.alex.homework4example.controller;
//
//import com.alex.homework4example.dto.TransactionDTO;
//import com.alex.homework4example.entity.Transaction;
//import com.alex.homework4example.mapper.impl.TransactionMapper;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TransactionController {
//
//    private final TransactionServiceImpl transactionService;
//    private final TransactionMapper transactionMapper;
//    private final ObjectMapper objectMapper;
//
//    @Autowired
//    public TransactionController(TransactionServiceImpl transactionService, TransactionMapper transactionMapper, ObjectMapper objectMapper) {
//        this.transactionService = transactionService;
//        this.transactionMapper = transactionMapper;
//        this.objectMapper = objectMapper;
//    }
//
//    public void createTransactions() throws JsonProcessingException {
//        TransactionDTO transaction1DTO = TransactionDTO.builder()
//                .amount(new java.math.BigDecimal("100.00"))
//                .transactionDate(java.time.LocalDateTime.now())
//                .sourceAccountId(1L)
//                .destinationAccountId(2L)
//                .currency("USD")
//                .build();
//
//        TransactionDTO transaction2DTO = TransactionDTO.builder()
//                .amount(new java.math.BigDecimal("200.00"))
//                .transactionDate(java.time.LocalDateTime.now())
//                .sourceAccountId(2L)
//                .destinationAccountId(1L)
//                .currency("EUR")
//                .build();
//
//        Transaction transaction1 = transactionMapper.toEntity(transaction1DTO);
//        Transaction transaction2 = transactionMapper.toEntity(transaction2DTO);
//
//        transactionService.create(transaction1);
//        transactionService.create(transaction2);
//
//        System.out.println("Created Transaction 1: " + objectMapper.writeValueAsString(transactionMapper.toDto(transaction1)));
//        System.out.println("Created Transaction 2: " + objectMapper.writeValueAsString(transactionMapper.toDto(transaction2)));
//    }
//
//    public void readTransaction(Long id) throws JsonProcessingException {
//        Transaction transaction = transactionService.findById(id)
//                .orElseThrow(() -> new RuntimeException("Transaction not found"));
//
//        TransactionDTO transactionDTO = transactionMapper.toDto(transaction);
//        System.out.println("Retrieved Transaction: " + objectMapper.writeValueAsString(transactionDTO));
//    }
//
//    public void updateTransaction(Long id) throws JsonProcessingException {
//        Transaction transaction = transactionService.findById(id)
//                .orElseThrow(() -> new RuntimeException("Transaction not found"));
//
//        TransactionDTO updatedTransactionDTO = TransactionDTO.builder()
//                .id(transaction.getId())
//                .amount(new java.math.BigDecimal("150.00"))
//                .transactionDate(transaction.getTransactionDate())
//                .sourceAccountId(transaction.getSourceAccount().getId())
//                .destinationAccountId(transaction.getDestinationAccount().getId())
//                .currency(transaction.getCurrency())
//                .build();
//
//        Transaction updatedTransaction = transactionMapper.toEntity(updatedTransactionDTO);
//        transactionService.update(updatedTransaction);
//
//        System.out.println("Updated Transaction: " + objectMapper.writeValueAsString(transactionMapper.toDto(updatedTransaction)));
//    }
//
//    public void deleteTransaction(Long id) {
//        transactionService.delete(id);
//        System.out.println("Deleted Transaction with ID: " + id);
//    }
//}
