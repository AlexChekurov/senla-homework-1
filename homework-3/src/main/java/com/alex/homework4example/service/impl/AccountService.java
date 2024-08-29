package com.alex.homework4example.service.impl;

import com.alex.homework4example.dao.impl.AccountDao;
import com.alex.homework4example.dto.AccountDTO;
import com.alex.homework4example.entity.Account;
import com.alex.homework4example.mapper.impl.AccountMapper;
import com.alex.homework4example.service.AbstractCrudService;
import org.springframework.stereotype.Service;

@Service
public class AccountService extends AbstractCrudService<Account, AccountDTO, Integer> {

    private final AccountDao accountDao;
    private final AccountMapper accountMapper;

    public AccountService(AccountDao accountDao, AccountMapper accountMapper) {
        this.accountDao = accountDao;
        this.accountMapper = accountMapper;
    }

    @Override
    protected AccountDao getDao() {
        return accountDao;
    }

    @Override
    protected AccountMapper getMapper() {
        return accountMapper;
    }

    @Override
    protected Integer getEntityId(Account account) {
        return account.getId();
    }
}
