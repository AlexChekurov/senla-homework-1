package com.alex.homework4example.controller;

import com.alex.homework4example.dto.AccountDTO;
import com.alex.homework4example.service.impl.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountController {

    private final AccountService accountService;
    private final ObjectMapper objectMapper;

    @Autowired
    public AccountController(AccountService accountService, ObjectMapper objectMapper) {
        this.accountService = accountService;
        this.objectMapper = objectMapper;
    }

    public void initializeAndPerformOperations() throws JsonProcessingException {
        AccountDTO account1 = AccountDTO.builder()
                .accountNumber("111222333")
                .accountType("SAVINGS")
                .balance(new java.math.BigDecimal("1000.00"))
                .currency("USD")
                .iban("US12345678901234567890")
                .build();

        AccountDTO account2 = AccountDTO.builder()
                .accountNumber("444555666")
                .accountType("CHECKING")
                .balance(new java.math.BigDecimal("2000.00"))
                .currency("EUR")
                .iban("DE09876543210987654321")
                .build();

        AccountDTO createdAccount1 = accountService.create(account1);
        AccountDTO createdAccount2 = accountService.create(account2);

        System.out.println("Retrieved Account 1: " + objectMapper.writeValueAsString(accountService.findById(createdAccount1.getId()).orElseThrow()));

        accountService.delete(createdAccount2.getId());

        AccountDTO updatedAccount1 = AccountDTO.builder()
                .id(createdAccount1.getId())
                .accountNumber(createdAccount1.getAccountNumber())
                .accountType("UPDATED_SAVINGS")
                .balance(createdAccount1.getBalance())
                .currency(createdAccount1.getCurrency())
                .iban(createdAccount1.getIban())
                .build();
        accountService.update(updatedAccount1);

        System.out.println("Updated Account 1: " + objectMapper.writeValueAsString(accountService.findById(updatedAccount1.getId()).orElseThrow()));
    }
}