package com.alex.homework4example.service.impl;

import com.alex.homework4example.dao.impl.AccountDao;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.service.AccountService;
import com.alex.homework4example.service.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends AbstractCrudService<Account> implements AccountService {

    private final AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    protected AccountDao getDao() {
        return accountDao;
    }

    @Override
    protected Long getEntityId(Account account) {
        return account.getId() != null ? account.getId() : null;
    }
}
