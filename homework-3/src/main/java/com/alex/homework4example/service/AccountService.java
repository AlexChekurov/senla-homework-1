package com.alex.homework4example.service;

import com.alex.homework4example.entity.Account;

import java.math.BigDecimal;

public interface AccountService extends CrudService<Account> {
    // Добавляем специфические методы для Account, если нужно
    void transferMoney(Account fromAccount, Account toAccount, BigDecimal amount);

}
