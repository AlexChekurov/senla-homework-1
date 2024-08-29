package com.alex.homework4example.mapper.impl;

import com.alex.homework4example.dto.AccountDTO;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper implements Mapper<Account, AccountDTO> {

    @Override
    public AccountDTO toDto(Account account) {
        return AccountDTO.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .iban(account.getIban())
                .createdAt(account.getCreatedAt())
                .build();
    }

    @Override
    public Account toEntity(AccountDTO accountDTO) {
        return Account.builder()
                .id(accountDTO.getId())
                .accountNumber(accountDTO.getAccountNumber())
                .accountType(accountDTO.getAccountType())
                .balance(accountDTO.getBalance())
                .currency(accountDTO.getCurrency())
                .iban(accountDTO.getIban())
                .createdAt(accountDTO.getCreatedAt())
                .build();
    }
}
