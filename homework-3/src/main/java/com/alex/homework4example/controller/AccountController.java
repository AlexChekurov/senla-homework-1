package com.alex.homework4example.controller;

import com.alex.homework4example.dto.AccountDTO;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.mapper.impl.AccountMapper;
import com.alex.homework4example.service.impl.AccountServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class AccountController {

    private final AccountServiceImpl accountService;
    private final AccountMapper accountMapper;
    private final ObjectMapper objectMapper;

    @Autowired
    public AccountController(AccountServiceImpl accountService, AccountMapper accountMapper, ObjectMapper objectMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.objectMapper = objectMapper;
    }

    public List<Account> createAccounts() throws JsonProcessingException {
        AccountDTO account1DTO = AccountDTO.builder()
                .accountNumber("111222333")
                .accountType("SAVINGS")
                .balance(new java.math.BigDecimal("1000.00"))
                .currency("USD")
                .iban("US12345678901234567890")
                .build();

        AccountDTO account2DTO = AccountDTO.builder()
                .accountNumber("444555666")
                .accountType("CHECKING")
                .balance(new java.math.BigDecimal("2000.00"))
                .currency("EUR")
                .iban("DE09876543210987654321")
                .build();

        Account account1 = accountMapper.toEntity(account1DTO);
        Account account2 = accountMapper.toEntity(account2DTO);

        accountService.create(account1);
        accountService.create(account2);

        System.out.println("Created Account 1: " + objectMapper.writeValueAsString(accountMapper.toDto(account1)));
        System.out.println("Created Account 2: " + objectMapper.writeValueAsString(accountMapper.toDto(account2)));

        return Arrays.asList(account1, account2);
    }


    public void readAccount(Long id) throws JsonProcessingException {
        Account account = accountService.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        AccountDTO accountDTO = accountMapper.toDto(account);
        System.out.println("Retrieved Account: " + objectMapper.writeValueAsString(accountDTO));
    }

    public void updateAccount(Long id) throws JsonProcessingException {
        Account account = accountService.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        AccountDTO updatedAccountDTO = AccountDTO.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .accountType("UPDATED_SAVINGS")
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .iban(account.getIban())
                .createdAt(account.getCreatedAt())
                .build();

        Account updatedAccount = accountMapper.toEntity(updatedAccountDTO);
        accountService.update(updatedAccount);

        System.out.println("Updated Account: " + objectMapper.writeValueAsString(accountMapper.toDto(updatedAccount)));
    }

    public void deleteAccount(Long id) {
        accountService.delete(id);
        System.out.println("Deleted Account with ID: " + id);
    }
}
