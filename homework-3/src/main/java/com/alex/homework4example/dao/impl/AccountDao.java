package com.alex.homework4example.dao.impl;

import com.alex.homework4example.dao.AbstractDao;
import com.alex.homework4example.entity.Account;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class AccountDao extends AbstractDao<Long, Account> {

    private final static AtomicLong idCounter = new AtomicLong(0);

    @Override
    protected Long generateId() {
        return idCounter.incrementAndGet();
    }

    @Override
    protected Long getId(Account account) {
        return account.getId() != null ? account.getId() : null;
    }

    @Override
    protected void setId(Account account, Long id) {
        account.setId(id);
    }
}
