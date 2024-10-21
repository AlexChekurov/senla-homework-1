package com.alex.homework4example.controller;

import com.alex.homework4example.dto.AccountDTO;
import com.alex.homework4example.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
@Validated
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public List<AccountDTO> getAllAccounts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "accountNumber", required = false) String accountNumber,
            @RequestParam(name = "accountType", required = false) String accountType,
            @RequestParam(name = "currency", required = false) String currency) {
        var params = new HashMap<String, Object>();
        if (StringUtils.hasLength(accountNumber)) {
            params.put("accountNumber", accountNumber);
        }

        if (StringUtils.hasLength(accountType)) {
            params.put("accountType", accountType);
        }

        if (StringUtils.hasLength(currency)) {
            params.put("currency", currency);
        }

        return accountService.findAll(page, size, params);
    }

    @GetMapping("/{accountId}")
    public AccountDTO getAccountById(@PathVariable("accountId") Long accountId) {
        return accountService.findDtoById(accountId);
    }

    @PostMapping
    public AccountDTO createAccount(@RequestBody @Valid AccountDTO account) {
        return accountService.create(account);
    }

    @PutMapping("/{accountId}")
    public AccountDTO updateAccount(
            @PathVariable("accountId") Long accountId,
            @RequestBody @Valid AccountDTO accountDetails) {
        return accountService.update(accountId, accountDetails);
    }

    @DeleteMapping("/{accountId}")
    @ResponseStatus(NO_CONTENT)
    public void deleteAccount(@PathVariable("accountId") Long id) {
        accountService.deleteById(id);
    }

}
