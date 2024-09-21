package com.alex.homework4example.controller;

import com.alex.homework4example.dto.AccountDTO;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.mapper.AccountMapper;
import com.alex.homework4example.service.impl.AbstractCrudService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class AccountController {

    private final AbstractCrudService<Account> accountService;
    private final ObjectMapper objectMapper;
    private final AccountMapper accountMapper;

    public List<Account> createAccounts() throws JsonProcessingException {

        accountService.deleteAll();

        AccountDTO account1DTO = AccountDTO.builder()
                .accountNumber("1112223133")
                .accountType("SAVINGS")
                .balance(new java.math.BigDecimal("1000.00"))
                .currency("USD")
                .iban("US12345658901234567890")
                .build();

        AccountDTO account2DTO = AccountDTO.builder()
                .accountNumber("4445551666")
                .accountType("CHECKING")
                .balance(new java.math.BigDecimal("2000.00"))
                .currency("EUR")
                .iban("DE09876543310987654321")
                .build();

        Account account1 = accountMapper.toEntity(account1DTO);
        Account account2 = accountMapper.toEntity(account2DTO);

        accountService.create(account1);
        accountService.create(account2);


        System.out.println("Created Account 1: " + objectMapper.writeValueAsString(accountMapper.toDto(account1)));
        System.out.println("Created Account 2: " + objectMapper.writeValueAsString(accountMapper.toDto(account2)));

        return Arrays.asList(account1, account2);
    }
}
