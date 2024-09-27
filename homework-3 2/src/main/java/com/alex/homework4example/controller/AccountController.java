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
        return accountService.findDtoById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find account with id: " + id));
    }

    public AccountDTO createAccount(AccountDTO account) {
        return accountService.create(account);
    }

    public AccountDTO updateAccount(Long id, AccountDTO accountDetails) {
        return accountService.findById(id)
                .map(account -> {
                    account.setAccountNumber(accountDetails.getAccountNumber());
                    account.setAccountType(accountDetails.getAccountType());
                    account.setBalance(accountDetails.getBalance());
                    account.setCurrency(accountDetails.getCurrency());
                    account.setIban(accountDetails.getIban());
                    return accountService.updateEntityToDto(account);
                })
                .orElseThrow(() -> new EntityNotFoundException("Can't update account with id: " + id));
    }

    public void deleteAccount(Long id) {
        accountService.deleteById(id);
    }
}
