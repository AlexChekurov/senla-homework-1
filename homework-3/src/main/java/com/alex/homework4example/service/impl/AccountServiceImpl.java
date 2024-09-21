package com.alex.homework4example.service.impl;

import com.alex.homework4example.dao.AbstractDao;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.exception.InsufficientFundsException;
import com.alex.homework4example.service.AccountService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl extends AbstractCrudService<Account> implements AccountService {

    public AccountServiceImpl(AbstractDao<Account> dao) {
        super(dao);
    }

    public void transferMoney(Account fromAccount, Account toAccount, BigDecimal amount) {

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Not enough funds in the source account");
        }
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

    }
}
