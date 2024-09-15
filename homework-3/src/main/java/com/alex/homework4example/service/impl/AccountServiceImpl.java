package com.alex.homework4example.service.impl;

import com.alex.homework4example.annotations.Transaction;
import com.alex.homework4example.dao.jdbc.impl.JdbcAccountDao;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.exception.InsufficientFundsException;
import com.alex.homework4example.service.AccountService;
import com.alex.homework4example.service.AbstractCrudService;
import com.alex.homework4example.utils.ConnectionHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl extends AbstractCrudService<Account> implements AccountService {

    private final JdbcAccountDao jdbcAccountDao;

    public AccountServiceImpl(ConnectionHolder connectionHolder) {
        this.jdbcAccountDao = new JdbcAccountDao(connectionHolder);
    }

    @Override
    protected JdbcAccountDao getDao() {
        return jdbcAccountDao;
    }

    @Transaction
    public void transferMoney(Account fromAccount, Account toAccount, BigDecimal amount) {
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Not enough funds in the source account");
        }
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        update(fromAccount);
        update(toAccount);
    }

}
