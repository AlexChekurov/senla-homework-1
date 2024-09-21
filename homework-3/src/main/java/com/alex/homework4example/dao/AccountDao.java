package com.alex.homework4example.dao;

import com.alex.homework4example.entity.Account;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDao extends AbstractDao<Account> {

    public AccountDao() {
        super(Account.class);
    }

}
