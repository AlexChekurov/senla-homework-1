package com.alex.homework4example.controller;

import com.alex.homework4example.dto.AccountDTO;
import com.alex.homework4example.exception.EntityNotFoundException;
import com.alex.homework4example.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class AccountController {

    private final AccountService accountService;


    public List<AccountDTO> getAllAccounts() {
        return accountService.findAll();
    }

    public AccountDTO getAccountById(Long id) {
        return accountService.findDtoById(id);
    }

    public AccountDTO createAccount(AccountDTO account) {
        return accountService.create(account);
    }

    public AccountDTO updateAccount(Long id, AccountDTO accountDetails) {
        return accountService.update(id, accountDetails);
    }

    public void deleteAccount(Long id) {
        accountService.deleteById(id);
    }
}
