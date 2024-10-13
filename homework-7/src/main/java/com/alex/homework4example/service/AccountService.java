package com.alex.homework4example.service;

import com.alex.homework4example.dto.AccountDTO;
import com.alex.homework4example.entity.Account;

import java.math.BigDecimal;

public interface AccountService extends CrudService<Account, AccountDTO> {
    void transferMoney(Account fromAccount, Account toAccount, BigDecimal amount);


}
