package com.alex.homework4example.controller;

import com.alex.homework4example.dto.AccountDTO;
import com.alex.homework4example.exception.EntityNotFoundException;
import com.alex.homework4example.service.AccountService;
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
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public List<AccountDTO> getAllAccounts() {
        return accountService.findAll();
    }

    @GetMapping("/{accountId}")
    public AccountDTO getAccountById(@PathVariable("accountId") Long accountId) {
        return accountService.findDtoById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find account with id: " + accountId));
    }

    @PostMapping
    public AccountDTO createAccount(@RequestBody AccountDTO account) {
        return accountService.create(account);
    }

    @PutMapping("/{accountId}")
    public AccountDTO updateAccount(@PathVariable("accountId") Long accountId, @RequestBody AccountDTO accountDetails) {
        return accountService.findById(accountId)
                .map(account -> {
                    account.setAccountNumber(accountDetails.getAccountNumber());
                    account.setAccountType(accountDetails.getAccountType());
                    account.setBalance(accountDetails.getBalance());
                    account.setCurrency(accountDetails.getCurrency());
                    account.setIban(accountDetails.getIban());
                    return accountService.updateEntityToDto(account);
                })
                .orElseThrow(() -> new EntityNotFoundException("Can't update account with accountId: " + accountId));
    }

    @DeleteMapping("/{accountId}")
    public void deleteAccount(@PathVariable("accountId") Long id) {
        accountService.deleteById(id);
    }

}
